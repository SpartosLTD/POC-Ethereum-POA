#!/usr/bin/env bash
#Updates folder with network setup on VM instances

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
gcloud compute ssh $BOOTSTRAP_INSTANCE_NAME --command="sudo rm -rf ~/poa"
gcloud compute scp --recurse --zone=${zones[1]} $NETWORK_FOLDER $BOOTSTRAP_INSTANCE_NAME:~/poa &

#Run setup script on instance
for (( i=1; i<=$COUNT; i++ ))
do
    let ZONE_INDEX=$i%7
    echo "Zone index $ZONE_INDEX"
    ZONE=${zones[$ZONE_INDEX]}
    INSTANCE_NAME=${GROUP}-${i}

    gcloud compute ssh $INSTANCE_NAME --zone=$ZONE --command="sudo rm -rf ~/poa"
    gcloud compute scp --recurse --zone=$ZONE $NETWORK_FOLDER $INSTANCE_NAME:~/poa &
done