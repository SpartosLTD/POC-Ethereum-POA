#!/usr/bin/env bash
GROUP=$1
COUNT=$2

echo "Starting network $GROUP"
./ethereum/run_command_for_every_instance.sh $GROUP $COUNT start
