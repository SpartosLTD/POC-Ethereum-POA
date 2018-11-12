#!/usr/bin/env bash

GROUP=${1:-ethnode}
COUNT=${2:-3}
BOOT_NODE_IP=$3
MAX_SEALERS_COUNT=${4:-0}

declare -A zones

zones[0]="europe-west3-b"
zones[1]="europe-west3-a"
zones[2]="europe-west3-b"
zones[3]="europe-west3-c"
zones[4]="europe-west2-b"
zones[5]="europe-west2-c"
zones[6]="europe-west1-b"
zones[7]="europe-west1-c"

#Run setup script on instanc
for (( i=1; i<=$COUNT; i++ ))
do
    let ZONE_INDEX=$i%7
    echo "Zone index $ZONE_INDEX"
    ZONE=${zones[$ZONE_INDEX]}
    INSTANCE_NAME=${GROUP}-${i}
    echo "Running node $INSTANCE_NAME"
    let NODE_FOLDER_INDEX=$i-1
    gcloud compute ssh $INSTANCE_NAME --zone=$ZONE --command="cd ~/poa; echo \"Echo\" ${NODE_FOLDER_INDEX} ${BOOT_NODE_IP}; ./startNodeByNumber.sh ${NODE_FOLDER_INDEX} ${BOOT_NODE_IP}" &
done