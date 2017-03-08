#!/bin/bash

setup_mongo()
{
    # Configure mongodb.list file with the correct location
    #sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6
    #echo "deb [ arch=amd64 ] http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list    
    
    # Install updates
    sudo apt-get -y update

    # Install Mongo DB
    sudo apt-get install -y mongodb

    # Bind to all ip addresses
    #sudo sed -i -e 's/bindIp: 127.0.0.1/bindIp: 0.0.0.0/g' /etc/mongod.conf
    #sudo service mongod restart

    # Start Mongo on startup
    #sudo systemctl enable mongod.service
}

setup_java()
{
    # Install Java JDK
    sudo apt-get install -y openjdk-8*

    # Create Folder
    sudo rm -rf /var/www
    sudo mkdir /var/www
    sudo mkdir /var/www/images
}

setup_api()
{

    echo "Setting up the API tier..."

    # Get files
    cd ~/
    sudo cp -r AzureX-API.war /var/www
    cd /var/www
    sudo chmod u+x AzureX-API.war
    sudo cat > /var/www/azurexapi.service <<EOF
[Unit]
Description=AzureOpenDevAPI
After=syslog.target

[Service]
ExecStart=/var/www/AzureX-API.war
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
EOF

    sudo cp azurexapi.service /etc/systemd/system/azurexapi.service 
    echo "API tier has completed its setup."
    
}

setup_web()
{
    
    echo "Setting up the web tier..."
    
    # Get files
    cd ~/
    sudo cp -r web.war /var/www
    cd /var/www
    sudo chmod u+x web.war
    sudo cat > ./azurexweb.service <<EOF
[Unit]
Description=AzureOpenDevWeb
After=syslog.target

[Service]
ExecStart=/var/www/web.war
SuccessExitStatus=143
Environment=INCIDENT_API_URL=http://localhost:9000
Environment=IMAGE_STORAGE_LOCATION=/var/www/images

[Install]
WantedBy=multi-user.target
EOF

    sudo cp azurexweb.service /etc/systemd/system/azurexweb.service 
    echo "Web tier has completed its setup."

}

start()
{
    echo "Starting Application"

    sudo systemctl enable azurexapi.service
    sudo systemctl start azurexapi.service
    sudo systemctl enable azurexweb.service
    sudo systemctl start azurexweb.service

}

setup_mongo
setup_java
setup_api
setup_web
start

exit 0
