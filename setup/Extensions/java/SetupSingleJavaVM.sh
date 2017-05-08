#!/bin/bash

# Usage Example
# sudo sh SetupSingleVM.sh "https://stage0f4d414b108104ae8a4.blob.core.windows.net" "nodebuildcitypowerapi" "nodebuildcitypower"
# sudo sh SetupSingleVM.sh "<URL to an Azure Storage Account>" "<container name for the API archive.zip>" "<container name for the Web archive.zip>"
AZURE_STORAGE_ACCOUNT_URL=$1
API_CONTAINER=$2
WEB_CONTAINER=$3

echo "Using API Container ${API_CONTAINER}"
echo "Using Web Container ${WEB_CONTAINER}"

do_update()
{
        sudo apt-get -y update
}

setup_mongo()
{
    # Configure mongodb.list file with the correct location
#    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6
#    echo "deb [ arch=amd64,arm64 ] http://repo.mongodb.org/apt/ubuntu "$(lsb_release -sc)"/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list
#    sudo apt-get update

    # Disable THP
#    sudo echo never > /sys/kernel/mm/transparent_hugepage/enabled
#    sudo echo never > /sys/kernel/mm/transparent_hugepage/defrag
#    sudo grep -q -F 'transparent_hugepage=never' /etc/default/grub || echo 'transparent_hugepage=never' >> /etc/default/grub

    # Install updates
#    sudo apt-get -y update

    # Modified tcp keepalive according to https://docs.mongodb.org/ecosystem/platforms/windows-azure/
#    sudo bash -c "sudo echo net.ipv4.tcp_keepalive_time = 120 >> /etc/sysctl.conf"

    # Install Mongo DB
    sudo apt-get install -y mongodb

    # Bind to all ip addresses
#    sudo sed -i -e 's/bindIp: 127.0.0.1/bindIp: 0.0.0.0/g' /etc/mongod.conf
    sudo service mongodb restart

    # Start Mongo on startup
#    sudo systemctl enable mongodb.service
}

setup_java()
{
    sudo apt-get install -y openjdk-8*
    sudo rm -rf /var/www
    sudo mkdir /var/www
}

setup_api()
{
    # Get files
    cd /var/www
    sudo mkdir /var/www/images    
    sudo rm -rf Azure-X-API.war
    sudo curl -O ${AZURE_STORAGE_ACCOUNT_URL}/${API_CONTAINER}/AzureX-API.war
    sudo chmod u+x AzureX-API.war
    sudo cat > /etc/systemd/system/azurexapi.service <<EOF
[Unit]
Description=AzureOpenDevAPI
After=syslog.target

[Service]
ExecStart=/var/www/AzureX-API.war
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
EOF

    sudo systemctl enable azurexapi.service
    sudo systemctl start azurexapi.service
}

setup_web()
{
    # Get files
    cd /var/www
    sudo rm -rf Azure-X-API.war
    sudo curl -O ${AZURE_STORAGE_ACCOUNT_URL}/${WEB_CONTAINER}/web.war
    sudo chmod u+x web.war
    sudo cat > /etc/systemd/system/azurexweb.service <<EOF
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

    sudo systemctl enable azurexweb.service
    sudo systemctl start azurexweb.service    
}

do_update
setup_mongo
setup_java
setup_api
setup_web

exit 0

