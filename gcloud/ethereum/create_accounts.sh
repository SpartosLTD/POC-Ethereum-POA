#!/bin/bash

folder=$1 #relative path to the folder to create accounts in
count=$2 #nodes count

mkdir $folder;
nodeid=0
rm address.txt
while [ $nodeid -lt $count ]
do
nodename='node'$nodeid
password='password'
rm -rf $nodename
mkdir $folder/$nodename
echo $password >> $folder/$nodename/password.txt
geth --verbosity 0 --datadir $folder/$nodename/ account new --password password.txt >> $folder/address.txt
((nodeid++))
done