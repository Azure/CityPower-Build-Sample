#!/bin/bash

# Usage Example
# sudo sh SetupSingleVM.sh "https://stage0f4d414b108104ae8a4.blob.core.windows.net" "nodebuildcitypowerapi" "nodebuildcitypower"
# sudo sh SetupSingleVM.sh "<URL to an Azure Storage Account>" "<container name for the API archive.zip>" "<container name for the Web archive.zip>"
#AZURE_STORAGE_ACCOUNT_URL=$1
#API_CONTAINER=$2
#WEB_CONTAINER=$3

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
    rm -rf frontend
    curl -O https://stage9f4d814b708544ae8a4.blob.core.windows.net/nodebuildcitypowerweb/archive.zip
    unzip archive.zip
    rm -rf archive.zip

    # Start Web
    PORT=80 API_URL=http://sfcitypowerapi.azurewebsites.net/api pm2 start ./node/app/web/app.js --name="CityPower.Web"
    pm2 startup
}

setup_node
setup_web

exit 0
