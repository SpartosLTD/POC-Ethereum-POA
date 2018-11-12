pragma solidity ^0.4.11;

import "./ERC223ReceivingContract.sol";
import "./SpartosToken.sol";

contract BettingContract is ERC223ReceivingContract {

    address private allowedSettler;

    address private operator;

    mapping(address => mapping(uint8 => uint)) private bets;
    mapping(uint8 => uint) private betsAmountForSide;

    address[] private players;

    uint8 odds_side_1;
    uint8 odds_side_2;

    uint8[] sidesToBet;

    uint transferredToOperatorAfterSettlement;

    uint private betsCount ;
    uint private firstBetTimestamp;
    uint private lastBetTimestamp ;

    uint private settlementsCount;
    uint private firstSettlementTimestamp;
    uint private lastSettlementTimestamp;

    bool private locked = false;

    bytes4 betFunctionSigner = whatTheHash("bet");

    string desc;

    SpartosToken spartosToken;

    event BetAccepted(address _player, uint _amount, uint8 _side);

    constructor(string _desc, address _spartosTokenContract, address _allowedSettler, address _operator, uint8 _odds_side_1, uint8 _odds_side_2) public {
        desc = _desc;
        allowedSettler = _allowedSettler;
        operator = _operator;
        odds_side_1 = _odds_side_1;
        odds_side_2 = _odds_side_2;
        spartosToken = SpartosToken(_spartosTokenContract);

        sidesToBet.push(1);
        sidesToBet.push(2);
    }

    function getDescription() view returns(string) {
        return desc;
    }

    function addPlayerIfNotExists(address player) private {
        //check player already exists
        bool existsingPlayer = false;
        for (uint8 i = 0; i < sidesToBet.length; i++) {
            if (bets[player][sidesToBet[i]] > 0) {
                existsingPlayer = true;
                break;
            }
        }
        if (!existsingPlayer) {
            players.push(player);
        }
    }

    function bet(address player, uint amount, uint8 side) public {

        require(locked == false, "Contract is locked so doesn't accept bets");
        require(side == 1 || side == 2); //support only these sides for now

        //place bet
        addPlayerIfNotExists(player);

        uint existingBet = bets[player][side];
        bets[player][side] += amount;
        betsAmountForSide[side] += amount;

        //cover the bet
        uint amountToCover;

        if (side == 1) {
            amountToCover = amount * odds_side_2;
        } else if (side == 2) {
            amountToCover = amount * odds_side_1;
        }

        uint _allowance = spartosToken.allowance(operator, address(this));
        require(_allowance >= amountToCover, "Can't accept bet: not enough operator funds to cover it");

        spartosToken.transferFrom(operator, address(this), amountToCover);

        //Collect metrics
        if (firstBetTimestamp == 0) {
            firstBetTimestamp = now;
        }
        lastBetTimestamp = now;
        betsCount++;
    }

    function getFirstBetTimestamp() public view returns (uint) {
        return firstBetTimestamp;
    }

    function getLastBetTimestamp() public view returns(uint) {
        return lastBetTimestamp;
    }

    function getBetsCount() view returns (uint) {
        return betsCount;
    }

    function getFirstSettlementTimestamp() public view returns (uint) {
        return firstSettlementTimestamp;
    }

    function getLastSettlementTimestamp() public view returns(uint) {
        return lastSettlementTimestamp;
    }

    function getSettlementsCount() public view returns (uint) {
        return settlementsCount;
    }

    function existingBet(address _player, uint8 _side) view returns (uint) {
        return bets[_player][_side];
    }

    function getTotalBets(uint8 side) view returns (uint) {
        return betsAmountForSide[side];
    }

    function getTransferredToOpeatorAmountAfterSettlement() view returns (uint) {
        require(locked, "Contract has not been settled yet");
        return transferredToOperatorAfterSettlement;
    }

    function getPlayersLength() view returns (uint) {
        return players.length;
    }

    function settleLight(uint8 side) public {
        for (uint i = 0; i < players.length; i++) {
            address player = players[i];
            spartosToken.transfer(player, 1);
            spartosToken.transfer(0x65eba5f67dab356f9c21a25b16d30c0ab503a1cf, 1); //operator.json  -> IT BREAKS TRANSACTION
        }

        //Collect metrics
        if (firstSettlementTimestamp == 0) {
            firstSettlementTimestamp = now;
        }
        lastSettlementTimestamp = now;
        settlementsCount++;
    }

    /**
    * side: Result of the game
    */
    function settle(uint8 side)  external {
        require(msg.sender == allowedSettler, "Not allowed to settle contract");
        require(side == 1 || side == 2 || side == 3, "Contract only supports results 1,2,3");

        //Collect metrics
        if (firstSettlementTimestamp == 0) {
            firstSettlementTimestamp = now;
        }
        lastSettlementTimestamp = now;
        settlementsCount++;

        //lock();
        for (uint8 i = 0; i < players.length; i++) {
            address player = players[i];
            for (uint8 j = 0; j < sidesToBet.length; j++) {
                if (side == sidesToBet[j]) {
                    //players wins
                    uint amount = bets[player][side] * (odds_side_1 + odds_side_2);
                    if (amount > 1) { //FIXME just for tests, remove this IF and send amount value
                        //spartosToken.transfer(player, bets[player][side] * (odds_side_1 + odds_side_2));
                        spartosToken.transfer(player, 1);
                    }
                } else if (side == 3) {
                    //3 is neutral results - just return bets to players

                    uint amountToReturn = bets[player][side];
                    if (amountToReturn > 1) { //FIXME just for tests, remove this IF and send amount value
                        //spartosToken.transfer(player, bets[player][side]);
                        spartosToken.transfer(player, 1);
                    }
                }
            }
        }
        //return rest of the tokens to operator
        //transferredToOperatorAfterSettlement = spartosToken.balanceOf(address(this)); //FIXME uncomment after tests
        transferredToOperatorAfterSettlement = 1; //FIXME uncomment after tests
        spartosToken.transfer(operator, transferredToOperatorAfterSettlement);
    }

    function lock() private {
        locked = true;
    }

    function tokenFallback(address _from, uint _value, bytes _data) {
        //TODO only SpartosToken contract supposed to call this function, implement restriction
        bytes4 signer = signerFromData(_data);
        if(signer == betFunctionSigner) {

            uint8 offset = 4;
            bytes4 sideBytes = bytesToBytes4(_data, offset);
            uint32 side = uint32(sideBytes);

            bet(_from, _value, uint8(side));
        }
    }

    function bytesToBytes16(bytes b, uint offset) private pure returns (bytes16) {
        bytes16 out;
        for (uint i = 0; i < 16; i++) {
            out |= bytes16(b[offset + i] & 0xFF) >> (i * 8);
        }
        return out;
    }

    function bytesToBytes4(bytes b, uint offset) private pure returns (bytes4) {
    bytes4 out;
    for (uint i = 0; i < 4; i++) {
        out |= bytes4(b[offset + i] & 0xFF) >> (i * 8);
    }
    return out;
}

    //Here you can enter a string name of method you need to execute
    //and receive its signer.
    function whatTheHash(string _src) constant returns (bytes4) {
        return bytes4(keccak256(_src));
    }

    //First four bytes are function signer
    //similar to Ether function calling signer
    function signerFromData(bytes _data) private returns (bytes4) {
        uint32 u = uint32(_data[3]) + (uint32(_data[2]) << 8) + (uint32(_data[1]) << 16) + (uint32(_data[0]) << 24);
        bytes4 sig = bytes4(u);
        return sig;
    }

}