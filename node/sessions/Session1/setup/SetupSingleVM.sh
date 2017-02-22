#!/bin/bash

# Usage Example
# sudo sh SetupSingleVM.sh "https://stage0f4d414b108104ae8a4.blob.core.windows.net" "nodebuildcitypowerapi" "nodebuildcitypower"
# sudo sh SetupSingleVM.sh "<URL to an Azure Storage Account>" "<container name for the API archive.zip>" "<container name for the Web archive.zip>"
AZURE_STORAGE_ACCOUNT_URL=$1
API_CONTAINER=$2
WEB_CONTAINER=$3

echo "Using API Container ${API_CONTAINER}"
echo "Using Web Container ${WEB_CONTAINER}"

setup_mongo()
{
    # Configure mongodb.list file with the correct location
    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6
    echo "deb [ arch=amd64,arm64 ] http://repo.mongodb.org/apt/ubuntu "$(lsb_release -sc)"/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list
    sudo apt-get update

    # Disable THP
    sudo echo never > /sys/kernel/mm/transparent_hugepage/enabled
    sudo echo never > /sys/kernel/mm/transparent_hugepage/defrag
    sudo grep -q -F 'transparent_hugepage=never' /etc/default/grub || echo 'transparent_hugepage=never' >> /etc/default/grub

    # Install updates
    sudo apt-get -y update

    # Modified tcp keepalive according to https://docs.mongodb.org/ecosystem/platforms/windows-azure/
    sudo bash -c "sudo echo net.ipv4.tcp_keepalive_time = 120 >> /etc/sysctl.conf"

    # Install Mongo DB
    sudo apt-get install -y mongodb-org

    # Bind to all ip addresses
    sudo sed -i -e 's/bindIp: 127.0.0.1/bindIp: 0.0.0.0/g' /etc/mongod.conf
    sudo service mongod restart

    # Start Mongo on startup
    sudo systemctl enable mongod.service
}

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
    curl -O ${AZURE_STORAGE_ACCOUNT_URL}/${API_CONTAINER}/archive.zip
    unzip archive.zip
    rm -rf archive.zip

    # Start API
    PORT=8080 DB_HOST=127.0.0.1 DB_PORT=27017 pm2 start ./node/app/api/app.js --name="CityPower.API"
}

setup_web()
{
    # Get files
    cd /var/www
    rm -rf frontend
    curl -O ${AZURE_STORAGE_ACCOUNT_URL}/${WEB_CONTAINER}/archive.zip
    unzip archive.zip
    rm -rf archive.zip

    # Start Web
    PORT=80 API_URL=http://localhost:8080/api pm2 start ./node/app/web/app.js --name="CityPower.Web"
}

setup_mongo
setup_node
setup_api
setup_web

exit 0
