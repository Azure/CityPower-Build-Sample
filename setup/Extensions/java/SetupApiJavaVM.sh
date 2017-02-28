#!/bin/bash

# Usage Example
# sudo sh SetupSingleVM.sh "https://stage0f4d414b108104ae8a4.blob.core.windows.net" "nodebuildcitypowerapi" "nodebuildcitypower"
# sudo sh SetupSingleVM.sh "<URL to an Azure Storage Account>" "<container name for the API archive.zip>" "<container name for the Web archive.zip>"

BUILD_ARTIFACT=$1
DB_CONNECT_STRING=$2
API_LISTEN_PORT=$3



do_update()
{
        sudo apt-get -y update
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
    sudo curl -O ${BUILD_ARTIFACT}
    sudo chmod u+x AzureX-API.war
    sudo cat > /etc/systemd/system/azurexapi.service <<EOF
[Unit]
Description=AzureOpenDevAPI
After=syslog.target

[Service]
ExecStart=/var/www/AzureX-API.war
SuccessExitStatus=143
Environment=DB_CONNECT_STRING=$DB_CONNECT_STRING
Environment=WEB_LISTEN_PORT=$WEB_LISTEN_PORT
[Install]
WantedBy=multi-user.target
EOF

    sudo systemctl enable azurexapi.service
    sudo systemctl start azurexapi.service
}

do_update
setup_java
setup_api


exit 0

