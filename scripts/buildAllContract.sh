#!/usr/bin/env bash

set -e
set -o pipefail

baseDir="../contracts"

buildContractsDir="../contracts"

targets="BettingContract"

for fileName in ${baseDir}/*.*; do

    cd $baseDir
    echo "Compiling Solidity file ${fileName}"

    fileNoFolder=$(basename ${fileName})
    fileNoExtension=${fileNoFolder%.*}

    echo "fileNoExtension ${fileNoExtension}"

    solc --bin --abi --optimize --overwrite \
            --allow-paths "$(pwd)" \
            ${baseDir}/${fileNoExtension}.sol -o ${buildContractsDir}/build/
    echo "Complete"

    echo "Generating contract bindings"
    web3j solidity generate \
        ${buildContractsDir}/build/${fileNoExtension}.bin \
        ${buildContractsDir}/build/${fileNoExtension}.abi \
        -p com.spartos.testclient.contracts.generated \
        -o ../testclient/src/main/java/ || true > /dev/null

    echo "Complete"

    cd -
done
