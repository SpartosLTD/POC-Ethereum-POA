#!/usr/bin/env bash
nodeid=$1
BOOT_NODE_IP=$2
NETWORK_ID=${3-2525}
arg=$((nodeid+1))p
str=$(sed -n $arg < address.txt)
address=$(sed 's/.*{\(.*\)}.*/\1/g' <<< $str)
echo $address

#Cleanup and init
nodename=node$nodeid
echo delete $nodename/geth
../geth/geth --datadir $nodename/ init genesis.json

nohup geth --datadir node$nodeid/ --targetgaslimit 30000000 --syncmode 'full'  --rpc --port 30320 --rpcaddr "0.0.0.0" --rpccorsdomain "*" --rpcport 8500 --rpcapi 'personal,db,eth,net,web3,txpool,miner' --bootnodes 'enode://53185cef138644c37b7aced1f9a9beb198ccb2bb634db57d834cb0a1c66406eb39039e7c3d986229ce56c11178a4486fbb19c529fd1bed0e2556dbf01c4400e6@'${BOOT_NODE_IP}':30310' --networkid $NETWORK_ID --gasprice '1' -unlock $address --password node$nodeid/password.txt --mine>> logs/node$nodeid.log
