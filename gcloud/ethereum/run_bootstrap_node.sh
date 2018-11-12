#!/usr/bin/env bash
GROUP_NAME=${1:-ethnode}
INSTANCE_NAME=$1'-bootstrap'
gcloud compute ssh $INSTANCE_NAME --zone=europe-west3-a --command="cd ~/poa; sh ./bootnode.sh"