const HDWalletProvider = require("truffle-hdwallet-provider");

const seed = "clown neither clever artist fork giggle remain chuckle corn degree video story";
const node = "http://127.0.0.1:8545/";

module.exports = {
  networks: {
    development: {
      provider: function() {
        return new HDWalletProvider(seed, node);
      },
      network_id: "*", // Match any network id
      gasPrice: 0,
      gas: 4500000
    }
  }
};