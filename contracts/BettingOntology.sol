pragma solidity ^0.4.24;

contract BettingOntology {

    mapping(address => uint) private balances;

    uint8 private betsCount;
    uint private firstBetTimestamp;
    uint private lastBetTimestamp ;

    uint private settlementsCount;
    uint private firstSettlementTimestamp;
    uint private lastSettlementTimestamp;


    struct Bet {
        uint8 id;
        address player;
        uint amount;
        uint8 outcome;
    }

    uint8[] private betIds;

    mapping(uint8 => Bet) private bets;

    address private owner;

    constructor() public {
        owner = msg.sender;
        balances[owner] = 100000000;
    }

    function transfer(address addressFrom, address addressTo, uint amount) public {
        uint senderAmount = balances[addressFrom];
        require(senderAmount >= amount);

        senderAmount -= amount;
        balances[addressFrom] = senderAmount;
        uint receiverAmount = balances[addressTo];
        receiverAmount += amount;
        balances[addressTo] = receiverAmount;
    }

    function placeBet(address player, uint amount, uint8 outcome) external {

        Bet memory newBet = Bet({
            id: betsCount,
            player: player,
            amount: amount,
            outcome: outcome
            });

        bets[newBet.id] = newBet;

        betIds.push(newBet.id);

        betsCount += 1;

        //Collect metrics
        if (firstBetTimestamp == 0) {
            firstBetTimestamp = now;
        }
        lastBetTimestamp = now;
    }

    function settle(uint8 outcome) external {

        for (uint8 i = 0; i < betIds.length; i++) {
            Bet memory bet = bets[betIds[i]];
            address player = bet.player;

            if (bet.outcome == outcome) {
                transfer(owner, player, bet.amount);
            }
        }


        //Collect metrics
        if (firstSettlementTimestamp == 0) {
            firstSettlementTimestamp = now;
        }
        lastSettlementTimestamp = now;
        settlementsCount++;
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



}