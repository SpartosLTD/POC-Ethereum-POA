#!/usr/bin/env bash

GROUP=${1:-ethnode}
COUNT=${2:-3}
NETWORK_FOLDER=${3:-$GROUP}

declare -A zones

zones[0]="europe-west3-b"
zones[1]="europe-west3-a"
zones[2]="europe-west3-b"
zones[3]="europe-west3-c"
zones[4]="europe-west2-b"
zones[5]="europe-west2-c"
zones[6]="europe-west1-b"
zones[7]="europe-west1-c"

BOOTSTRAP_INSTANCE_NAME=${GROUP}'-bootstrap'
echo "Bootstrapping node $BOOTSTRAP_INSTANCE_NAME"
gcloud compute ssh $BOOTSTRAP_INSTANCE_NAME --zone=europe-west3-a --command="bash -s" <./bootstrap/new_eth_node_bootstrap.sh
gcloud compute scp --recurse $NETWORK_FOLDER $BOOTSTRAP_INSTANCE_NAME:~/poa

#Run setup script on instance
for (( i=1; i<=$COUNT; i++ ))
do
    let ZONE_INDEX=$i%7
    echo "Zone index $ZONE_INDEX"
    ZONE=${zones[$ZONE_INDEX]}
    INSTANCE_NAME=${GROUP}-${i}
    echo "Bootstrapping node $INSTANCE_NAME"
    gcloud compute ssh $INSTANCE_NAME --zone=$ZONE --command="bash -s" <./bootstrap/new_eth_node_bootstrap.sh
    gcloud compute scp --recurse $NETWORK_FOLDER $INSTANCE_NAME:~/poa
done