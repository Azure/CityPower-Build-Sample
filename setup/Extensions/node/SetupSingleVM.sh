#!/bin/bash

setup_mongo()
{
    # Configure mongodb.list file with the correct location
    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6
    echo "deb [ arch=amd64 ] http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list    
    
    # Install updates
    sudo apt-get -y update

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

    # Install PM2
    # http://pm2.keymetrics.io/
    npm install -g pm2
    sudo pm2 delete all

    # Create Folder
    rm -rf /var/www
    mkdir /var/www
}

setup_api()
{

    echo "Setting up the API tier..."

    # Get files
    cd ~/
    sudo cp -r api/ /var/www
    cd /var/www/api
    sudo npm install
    
    echo "API tier has completed its setup."
    
}

setup_web()
{
    
    echo "Setting up the web tier..."
    
    # Get files
    cd ~/
    sudo cp -r web/ /var/www
    cd /var/www/web
    sudo npm install

    echo "Web tier has completed its setup."

}

start()
{
    echo "Starting Application"

    cd ~/
    sudo cp citypower.config.js /var/www
    sudo pm2 start /var/www/citypower.config.js

}

setup_mongo
setup_node
setup_api
setup_web
start

exit 0
