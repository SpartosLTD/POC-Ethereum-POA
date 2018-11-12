#!/usr/bin/env bash
GROUP=$1
COUNT=$2
TYPE=${3:-n1-standard-4}

echo "Creating network $GROUP of $COUNT instances of type $TYPE + 1 bootnode instance"
./ethereum/create_instances.sh $GROUP $COUNT $TYPE
