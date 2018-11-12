#!/usr/bin/env bash

GROUP=$1 #group name == relative path to the folder to create accounts in
COUNT=$2 #nodes count
BOOTNODE_IP=$3
SEALER_NODES_COUNT=$4


#Step 1 Copy initial files
echo "Copying initial files"
cd ethereum
./copy_initial_files.sh $GROUP

#Step 2
echo "Creating accounts for $COUNT nodes in network $GROUP"
./create_accounts.sh $GROUP $COUNT

#Step 3
NETWORK_FOLDER=../ethereum/$GROUP/
echo "Setting up genesis.json file in $NETWORK_FOLDER for new network"
cd ../test_automation/
NETWORK_FOLDER=$NETWORK_FOLDER MAX_SEALERS=$SEALER_NODES_COUNT npm run genesis

#Step 4
echo "Bootstrapping VM instances with geth and network config"
cd ../ethereum/
./bootstrap_instances.sh $GROUP $COUNT

#Step 5
echo "Starting boot node"
./run_bootstrap_node.sh $GROUP &
sleep 3 #make sure sealer nodes run after bootnode

#Step 6
echo "Starting sealer nodes"
./run_instances.sh $GROUP $COUNT $BOOTNODE_IP $SEALER_NODES_COUNT
