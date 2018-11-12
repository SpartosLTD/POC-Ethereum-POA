pragma solidity ^0.4.11;

contract ERC223 {
    uint public totalSupply;
    function balanceOf(address who) constant returns (uint);
    function transfer(address to, uint value);
    function transfer(address to, uint value, bytes data);
    event TransferWithData(address indexed from, address indexed to, uint value, bytes data);
}
