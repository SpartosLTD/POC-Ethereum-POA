#Test Automation

##Two scripts helping you to run performance tests are available:

1. setupgenesis.js -> creating genesis.json file with network params (validator nodes list, initial balances etc.)
2. getNodeUrlsForPostman.js -> creates an array of node urls of deployed network, so you can copy-paste it to nodeUrls request param when making http requests to run performance tests 

##Preparation:
1. Install gcloud CLI and ssh keys: make sure you can login to node manually using gcloud compute ssh [instance_name];
2. In package.json, GOOGLE_APPLICATION_CREDENTIALS env varibale for "start" script points to credentials file generated on google cloud console. 
Please make your own and change path to it in package.json accrodingly.