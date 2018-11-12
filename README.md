# Project purpose

This project was created to measure performance (TPS - transactions per second) in Ethereum network with PoA (Proof-of-Authority) consensus algorithm. Specifically, we were interested in bet placement and settlement transactions throughput from Spartos platform design standpoint.  

# Сontents

## 1. Betting contracts
Betting contracts used in tests are in 'contracts' folder. To compile them to java, use 'scripts/buildAllContracts.sh'. You need web3j command line tool to make it work.

## 2. Private network deployment
gcloud folder contains scripts for private network deployment on Google Cloud Platform. You should install Google Cloud cli and setup project in order to use it.
Inside gcloud, you'll find 'test_automation' folder with NPM scripts to generate genesis.json and othere helper ones, please see descriptions inside.

## 3. Test client
Test client implemented as Spring Boot based web application, it's inside 'testclient' folder. Use http request to deploy smart contracts, launch sets of bet placement and settlement transactions, observe metrics.