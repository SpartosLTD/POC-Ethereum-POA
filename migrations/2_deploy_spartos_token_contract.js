var SpartosTokenContract = artifacts.require("SpartosToken");

module.exports = function(deployer) {
  deployer.deploy(SpartosTokenContract)
};