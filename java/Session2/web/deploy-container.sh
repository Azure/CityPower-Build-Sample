#!/usr/bin/env bash

# -----------------------------------------------------------------------------------------------
# This script is a poor man way of deploying docker containers onto Docker nodes running in cloud
# -----------------------------------------------------------------------------------------------
echo 'saving container to tar file'
docker save "azurecat-gsi/javaapp" > azurecat-gsi-javaapp.tar

echo "Copying tar ball to docker node"
scp -i ~/.ssh/{yourkey}.pem azurecat-gsi-javaapp.tar {replace}@{replace}:~/
echo "Now copying bash scripts"
scp -i ~/.ssh/{yourkey}.pem run-container.sh {replace}@{replace}:~/
 
ssh -i ~/.ssh/{yourkey}.pem {replace}@{dockernode} 'docker load < azurecat-gsi-javaapp.tar'
ssh -i ~/.ssh/{yourkey}.pem {replace}@{dockernode} './run-container.sh'

