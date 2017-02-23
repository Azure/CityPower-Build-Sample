#!/bin/bash

# Usage Example
# sudo sh SetupApiVM.sh https://azurexjenkinsartifacts.blob.core.windows.net/nodebuildcitypowerapi/archive.zip \"mongoddbsf.documents.azure.com\" \"10250\" \"citypower\" \"mongoddbsf\" \"vTiA7jGoHmMr7eZKenq3T4jlqvG2Msk2sxeUK5w0FUp4LAQieEx5SWnsGtjJBHY33JJNea20GUFwIQyBqXydgg==\" \"true\"
# sudo sh SetupApiVM.sh "<URL to an Azure Storage Account archive.zip file>" "<URL to an API>" "<Azure Storage Account Name>" "<Azure Storage Account Key>" "<Redis Host Name>" "<Redis Key>" "<Redis SSL Port>" "<App Insights Key>"

BUILD_FILE=$1

# Setup Environment Variables
if [ $1 ]
then
	echo "Setting up environment variables"
    echo "BUILD_ARTIFACT=$1" >> /etc/environment
	echo "DB_HOST=$2" >> /etc/environment
	echo "DB_PORT=$3" >> /etc/environment
	echo "DB_DATABASE=$4" >> /etc/environment
	echo "DB_USER=$5" >> /etc/environment
	echo "DB_PASSWORD=$6" >> /etc/environment
	echo "DB_SSL=$7" >> /etc/environment
	echo "Finished setting up environment variables"
else
	echo "No arguments passed to script. Skipping environment variable setup"
fi

setup_node()
{
    # Install NodeJS
    curl -sL https://deb.nodesource.com/setup_7.x | sudo -E bash -
    apt-get install -y nodejs
    apt-get install -y build-essential

    # Install PM2
    # http://pm2.keymetrics.io/
    npm install -g pm2
    pm2 delete all

    # Install unzip utility
    apt-get -y install unzip
    rm -rf /var/www
    mkdir /var/www
}

setup_api()
{
    # Get files
    cd /var/www
    rm -rf api
    curl -O https://stage9f4d814b708544ae8a4.blob.core.windows.net/nodebuildcitypowerapi/archive.zip
    unzip archive.zip
    rm -rf archive.zip

    # Start API
    PORT=80 pm2 start ./node/app/api/app.js --name="CityPower.API"
    #pm2 startup
}

setup_node
setup_api

exit 0
