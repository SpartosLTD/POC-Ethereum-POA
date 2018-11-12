pragma solidity ^0.4.11;

import "./BettingContract.sol";
import "./SpartosToken.sol";

contract BettingContractFactory {

    address[] private bettingContracts;

    event BettingContractCreated(address indexed contractAddress, string desc);

    function create(string _desc,
        address _spartosTokenContract,
        address _allowedSettler,
        address _operator,
        uint8 _odds_side_1,
        uint8 _odds_side_2) public returns (address) {

        BettingContract newContract = new BettingContract(_desc, _spartosTokenContract, _allowedSettler, _operator,
            _odds_side_1, _odds_side_2);

        bettingContracts.push(address(newContract));

        emit BettingContractCreated(address(newContract), _desc);

        return address(newContract);
    }

    function count() view public returns (uint) {
        return bettingContracts.length;
    }

    function getContract(uint index) public view returns (address) {
        return bettingContracts[index];
    }


}