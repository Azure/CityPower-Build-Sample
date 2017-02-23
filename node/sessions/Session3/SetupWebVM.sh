#!/bin/bash

# Usage Example
# sudo sh SetupWebVM.sh https://stage9f4d814b708544ae8a4.blob.core.windows.net/nodebuildcitypowerweb/archive.zip "http://sfcitypowerapi.azurewebsites.net/api" "sfcitypower" "mieM4iNn6VYOhuFrRE3x+z4e3lRTYTSVX3Dd+pvf3aNGj5yxOynYmnbOxf9t4ITz7Awmjq97Q3o/u/+7XNerag==" "sfcitypower.redis.cache.windows.net" "Ml4CSqp3emn3fAQu0Y6u7wdYdI1Cahz7EQC6uqYB7mI=" "6380" "b1345a11-c5e2-4c66-8e32-fce396cb489d"
# sudo sh SetupWebVM.sh "<URL to an Azure Storage Account archive.zip file>" "<URL to an API>" "<Azure Storage Account Name>" "<Azure Storage Account Key>" "<Redis Host Name>" "<Redis Key>" "<Redis SSL Port>" "<App Insights Key>"

BUILD_FILE=$1

# Setup Environment Variables
if [ $1 ]
then
{
	echo "Setting up environment variables"
	echo "BUILD_ARTIFACT=$1" >> /etc/environment
	echo "API_URL=$2" >> /etc/environment
	echo "AZURE_STORAGE_ACCOUNT=$3" >> /etc/environment
	echo "AZURE_STORAGE_ACCESS_KEY=$4" >> /etc/environment
	echo "REDISCACHE_HOSTNAME=$5" >> /etc/environment
	echo "REDISCACHE_PRIMARY_KEY=$6" >> /etc/environment
	echo "REDISCACHE_SSLPORT=$7" >> /etc/environment
	echo "APPINSIGHTS_INSTRUMENTATIONKEY=$8" >> /etc/environment
	echo "Finished setting up environment variables"
}
else
    echo "No variables detected. Skipping."
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

setup_web()
{
    # Get files
    cd /var/www
    echo "Retrieving ${BUILD_FILE}"
    curl -O ${BUILD_FILE}
    unzip archive.zip
    rm -rf archive.zip

    # Start Web
    PORT=80 pm2 start ./node/app/web/app.js --name="CityPower.Web"
    pm2 startup
}

setup_node
setup_web

exit 0
