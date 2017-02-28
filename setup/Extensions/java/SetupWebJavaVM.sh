#!/bin/bash

# Usage Example
# sudo sh SetupSingleVM.sh "https://stage0f4d414b108104ae8a4.blob.core.windows.net" "nodebuildcitypowerapi" "nodebuildcitypower"
# sudo sh SetupSingleVM.sh "<URL to an Azure Storage Account>" "<container name for the API archive.zip>" "<container name for the Web archive.zip>"

BUILD_ARTIFACT=$1
AZURE_STORAGE_ACCOUNT_URL=$1
INCIDENT_API_URL=$4
AZURE_STORAGE_ACCOUNT=$5
AZURE_STORAGE_ACCESS_KEY=$6
AZURE_STORAGE_BLOB_CONTAINER=$7
WEB_LISTEN_PORT=$8

echo "Using API Container ${API_CONTAINER}"
echo "Using Web Container ${WEB_CONTAINER}"

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
Environment=INCIDENT_API_URL=$INCIDENT_API_URL
Environment=AZURE_STORAGE_ACCOUNT=$AZURE_STORAGE_ACCOUNT
Environment=AZURE_STORAGE_ACCESS_KEY=$AZURE_STORAGE_ACCESS_KEY
Environment=AZURE_STORAGE_BLOB_CONTAINER=$AZURE_STORAGE_BLOB_CONTAINER
Environment=WEB_LISTEN_PORT=$WEB_LISTEN_PORT

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

