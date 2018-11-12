#!/bin/bash

folder=$1 #relative path to the folder to setup network in

mkdir $folder;
cp -r ./new_network_initial_files/* $folder