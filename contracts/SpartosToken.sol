pragma solidity ^0.4.24;

import "./ERC223Token.sol";

contract SpartosToken is ERC223Token {

    string public name;
    string public symbol;
    uint8 public decimals;
    uint256 public totalSupply;

    constructor() public {
        totalSupply = 10000000;
        name = "SpartosToken";
        decimals = 0;
        symbol = "SPARTOS";
        balances[msg.sender] = totalSupply;
    }
    
    // Function to access name of token .
    function name() public view returns (string _name) {
        return name;
    }
    // Function to access symbol of token .
    function symbol() public view returns (string _symbol) {
        return symbol;
    }
    // Function to access decimals of token .
    function decimals() public view returns (uint8 _decimals) {
        return decimals;
    }
    // Function to access total supply of tokens .
    function totalSupply() public view returns (uint256 _totalSupply) {
        return totalSupply;
    }
    // Function to access the balance of an address .
    function balanceOf(address _owner) public view returns (uint balance) {
        return balances[_owner];
    }

}