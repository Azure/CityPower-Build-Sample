#!/bin/bash

# Usage Example
# sudo sh SetupSingleVM.sh "https://stage0f4d414b108104ae8a4.blob.core.windows.net" "nodebuildcitypowerapi" "nodebuildcitypower"
# sudo sh SetupSingleVM.sh "<URL to an Azure Storage Account>" "<container name for the API archive.zip>" "<container name for the Web archive.zip>"

echo "Running setup file"
echo $1
echo $2
echo $3
echo $4
echo $5
echo $6
echo $7
echo $8
echo $9
echo $10


BUILD_ARTIFACT=$1
INCIDENT_API_URL=$2
AZURE_STORAGE_ACCOUNT=$3
AZURE_STORAGE_ACCESS_KEY=$4
AZURE_STORAGE_BLOB_CONTAINER=$5
WEB_LISTEN_PORT=$6
REDISCACHE_HOSTNAME=$7
REDISCACHE_SSLPORT=$8
REDISCACHE_PRIMARY_KEY=$9

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

setup_web()
{
    # Get files
    cd /var/www
    sudo rm -rf web.war
    sudo curl -O ${BUILD_ARTIFACT}
    sudo chmod u+x web.war
    sudo cat > /etc/systemd/system/azurexweb.service <<EOF
[Unit]
Description=AzureOpenDevWeb
After=syslog.target

[Service]
ExecStart=/var/www/web.war
SuccessExitStatus=143
Environment=INCIDENT_API_URL=$INCIDENT_API_URL
Environment=AZURE_STORAGE_ACCOUNT=$AZURE_STORAGE_ACCOUNT
Environment=AZURE_STORAGE_ACCESS_KEY=$AZURE_STORAGE_ACCESS_KEY
Environment=AZURE_STORAGE_BLOB_CONTAINER=$AZURE_STORAGE_BLOB_CONTAINER
Environment=WEB_LISTEN_PORT=$WEB_LISTEN_PORT
Environment=REDISCACHE_HOSTNAME=$REDISCACHE_HOSTNAME
Environment=REDISCACHE_PRIMARY_KEY=$REDISCACHE_PRIMARY_KEY
Environment=REDISCACHE_SSLPORT=$REDISCACHE_SSLPORT

[Install]
WantedBy=multi-user.target
EOF

    sudo systemctl enable azurexweb.service
    sudo systemctl start azurexweb.service    
}

do_update
setup_java
setup_web

exit 0

