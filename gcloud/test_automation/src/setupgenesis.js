const path = process.env.NETWORK_FOLDER;
const maxSealers = process.env.MAX_SEALERS;

const NODE_BALANCE = "0x200000000000000000000000000000000000000000000000000000000000000";

const genesisPath = "../" + path + "genesis.json";
const genesis = require(genesisPath);

async function start() {
    console.log("Setting up genesis.json file in " + path);
    const modifications = await genesisModification(path + "address.txt");

    const ownerAndOperatorAllocations = require("./spartosOwnerAndOperatorAllocations.js");

    const newGenesisJson = {...genesis, ...modifications};

    newGenesisJson.alloc = {...newGenesisJson.alloc, ...ownerAndOperatorAllocations};

    console.log(newGenesisJson);

    await saveFile(path + "genesis.json", JSON.stringify(newGenesisJson));

    //TODO allocate funds for default accounts (owner.json, operator.json) here
}

/*
returns modifications needs to be made to genesis.json file, to alloc and extraData fields in particular
*/

function genesisModification(addressFile) {

    return new Promise(function (resolve, reject) {
            var lineReader = require('readline').createInterface({
                input: require('fs').createReadStream(addressFile)
            });

            const addresses = [];
            var sealersCount = 0;
            var sealers = "";
            lineReader.on('line', function (line) {
                //Parse addresses
                var matches = line.match(/\{(.*?)\}/);
                if (matches) {
                    var address = matches[1];
                    if (maxSealers) {
                        if (sealersCount < maxSealers) {
                            //add sealer
                            sealersCount++;
                            sealers = sealers + address;
                        }
                    } else {
                        //no sealers limit set
                        sealers = sealers + address;
                    }
                    addresses.push(address);
                }
            });
            lineReader.on('close', () => {

                const alloc = {};
                addresses.forEach(address => {
                    alloc[address] = {"balance": NODE_BALANCE }
                });

                resolve({
                    alloc,
                    extraData: "0x0000000000000000000000000000000000000000000000000000000000000000" + sealers + "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                });

            });
        }
    )
}

async function saveFile(target, source) {
    return new Promise(function (resolve, reject) {
        var fs = require('fs');
        fs.writeFile(target, source, function(err) {
            if(err) {
                reject(err);
            }
            resolve();
            console.log("The file was saved!");
        });
    });
}

start();
