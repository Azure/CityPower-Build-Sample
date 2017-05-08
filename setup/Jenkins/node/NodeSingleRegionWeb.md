# NodeSingleRegionWeb

In a Jenkins server, [create](https://wiki.jenkins-ci.org/display/JENKINS/Building+a+software+project#Buildingasoftwareproject-Settinguptheproject) a new Free Style Project named **NodeSingleRegionWeb**. 

The following sections correspond to the new project, and contain configuration values.

## General

* Project name: NodeSingleRegionWeb

* Description: Signs into the Azure CLI with a Service Principal and adds a Custom Script Extension to a set of VMs. The Custom Script Extension is created with arguments corresponding to the machine's desired enviroment variables.

* GitHub Project URL: None

## Source Code Management

None

## Build Triggers

To enable automatic releases for the API when a build is done, check the box next to **Build after other projects are built** and select **NodeBuildWeb**.

## Build Environment

To start with a fresh environment each time, check the box **Delete workspace before build starts**.

## Build

The shell script depends on a few pre-requisites:

* [Create a Service Principal](https://docs.microsoft.com/en-us/cli/azure/create-an-azure-service-principal-azure-cli) either from the Portal or Azure CLI with the Contributor role and assigned to the Resource Groups (or entire Azure Subscription)

* Update the variables below with your values for Build Artifact location (specified in the NodeBuildApi project) and DocumentDB instance settings.

Execute shell command:

```sh
# Service Principal Username
SP_USERNAME="azureopendevsp"

# Service Prinicpal Passsword
SP_PW="P@ssword1opendev"

# Service Principal Tenant
SP_TENANT="00000000-0000-0000-0000-000000000000"

# Target VM
VMINSTANCES="webVM-0,webVM-1"

# Resource Group Containing the target VMSS
RG="OpenDev-SingleRegion-Node"

# Environment Variables for application
BUILD_ARTIFACT="https://opendevjenkinsartifacts.blob.core.windows.net/nodebuildweb/archive.zip"

API_URL="http://10.0.2.4/api"

AZURE_STORAGE_ACCOUNT="azurexstgwyk4z2fe2uxr4"

AZURE_STORAGE_ACCESS_KEY="jXdA2OGvOAxdNxl8zXeNp211wRK5tVJ7A8PcILoK5AFwQ6qYsL7hk84zINPL8TPnFhy3qaVL0AUs2G2Th/fSoA=="

REDISCACHE_HOSTNAME="rediscache-wyk4z2fe2uxr4.redis.cache.windows.net"

REDISCACHE_PRIMARY_KEY="GFI1G3DVPevP26AW6vs4TMQFnBUvv2cI4MyIUzvuLpE="

REDISCACHE_SSLPORT="6380"

APPINSIGHTS_INSTRUMENTATIONKEY=""

# Extension Script URL
SCRIPT="https://opendevjenkinsartifacts.blob.core.windows.net/scripts/setup/Extensions/node/SetupWebVMSS.sh"

# Script to Execute
SCRIPT_COMMAND='sh SetupWebVMSS.sh '${BUILD_ARTIFACT}' \"'${API_URL}'\" \"'${AZURE_STORAGE_ACCOUNT}'\" \"'${AZURE_STORAGE_ACCESS_KEY}'\" \"'${REDISCACHE_HOSTNAME}'\" \"'${REDISCACHE_PRIMARY_KEY}'\" \"'${REDISCACHE_SSLPORT}'\" \"'${APPINSIGHTS_INSTRUMENTATIONKEY}'\"'

####################################################################################

# Login to Azure CLI via a Service Principal
# https://docs.microsoft.com/en-us/cli/azure/authenticate-az-cli2#service-principal
az account clear
az login \
	--service-principal \
    --username 	${SP_USERNAME} \
    --password 	${SP_PW} \
    --tenant 	${SP_TENANT}

# Set subscription if Service Principal is used for multiple subscriptions
# az account set --subscription 00000000-0000-0000-0000-000000000000

# Create a JSON object representing the Custom Script Extension's Settings
NEWSETTINGS='{"fileUris": ["'${SCRIPT}'?build='${BUILD_NUMBER}'"], "commandToExecute": "'${SCRIPT_COMMAND}'"}'

# Deploy to each VM 
echo "Starting deploying to VMs"
for VMINSTANCE in $(echo $VMINSTANCES | sed "s/,/ /g")
do

  # Update the VM with an Extension
  az vm extension set \
    --name            	"CustomScript" \
    --publisher       	Microsoft.Azure.Extensions \
    --resource-group  	${RG} \
    --vm-name       	${VMINSTANCE} \
    --settings        	"${NEWSETTINGS}"

done

echo "Finished deploying"
```

## Post-build Actions

None
