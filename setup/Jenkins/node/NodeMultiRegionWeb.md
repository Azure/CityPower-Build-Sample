# NodeMultiRegionWeb

In a Jenkins server, [create](https://wiki.jenkins-ci.org/display/JENKINS/Building+a+software+project#Buildingasoftwareproject-Settinguptheproject) a new Free Style Project named **NodeMultiRegionWeb**. 

The following sections correspond to the new project, and contain configuration values.

## General

* Project name: NodeMultiRegionWeb

* Description: Signs into the Azure CLI with a Service Principal and adds a Custom Script Extension to a set of Virtual Machine Scale Sets located in multiple regions. The Custom Script Extension is created with arguments corresponding to the machine's desired enviroment variables.

* GitHub Project URL: None

## Source Code Management

None

## Build Triggers

To enable automatic releases for the web tier when a build is done, check the box next to **Build after other projects are built** and select **NodeBuildWeb**.

## Build Environment

To start with a fresh environment each time, check the box **Delete workspace before build starts**.

## Build

The shell script depends on a few pre-requisites:

* [Create a Service Principal](https://docs.microsoft.com/en-us/cli/azure/create-an-azure-service-principal-azure-cli) either from the Portal or Azure CLI with the Contributor role and assigned to the Resource Groups (or entire Azure Subscription)

* Update the variables below with your values for Build Artifact location (specified in the NodeBuildApi project), Application Insights insturmentation key, Azure Redis Cache settings, and Azure Storage Account details.

Execute shell command:

```sh
# Service Principal Username
SP_USERNAME="azureopendevsp"

# Service Prinicpal Passsword
SP_PW="P@ssword1opendev"

# Service Principal Tenant
SP_TENANT="00000000-0000-0000-0000-000000000000"

# Target VMSS
VMSS="webVM"

# Resource Group Containing the target VMSS
RG="OpenDev-MultiRegion-Node-"
RGREGIONS="westus,eastus"

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

# Deploy to each region
echo "Starting deploying to regions - $RGREGIONS"
for REGION in $(echo $RGREGIONS | sed "s/,/ /g")
do

  # Environment Variables for application
  BUILD_ARTIFACT="https://opendevjenkinsartifacts.blob.core.windows.net/nodebuildweb/archive.zip"

  API_URL="http://10.0.2.4/api"
  
  AZURE_STORAGE_ACCOUNT="stgazurexz4jsvrtij7bry"
  
  AZURE_STORAGE_ACCESS_KEY="Ey3alOdNrCX+BW9rVKkkxF310ltW+9RN5/RbWaEiOy4eUXuf7ujL3P8WGeg5VBfT9hwWlYXV5MvqiH57aY87yg=="
  
  REDISCACHE_HOSTNAME="rediscache-qv4idohti3as2.redis.cache.windows.net"
  
  REDISCACHE_PRIMARY_KEY="WYGSvIkYuSMdFNFSIySYPzvTTCKQnJ8soiWXVjH4n/A="
  
  REDISCACHE_SSLPORT="6380"
  
  APPINSIGHTS_INSTRUMENTATIONKEY="fe6ae87f-0d48-413c-bfc9-8df70ae5db73"
  
  # Extension Script URL
  SCRIPT="https://opendevjenkinsartifacts.blob.core.windows.net/scripts/setup/Extensions/node/SetupWebVMSS.sh"
  
  # Script to Execute
  SCRIPT_COMMAND='sh SetupWebVMSS.sh '${BUILD_ARTIFACT}' \"'${API_URL}'\" \"'${AZURE_STORAGE_ACCOUNT}'\" \"'${AZURE_STORAGE_ACCESS_KEY}'\" \"'${REDISCACHE_HOSTNAME}'\" \"'${REDISCACHE_PRIMARY_KEY}'\" \"'${REDISCACHE_SSLPORT}'\" \"'${APPINSIGHTS_INSTRUMENTATIONKEY}'\" \"'${REGION}'\"'

  # Create a JSON object representing the Custom Script Extension's Settings
  NEWSETTINGS='{"fileUris": ["'${SCRIPT}'?build='${BUILD_NUMBER}'"], "commandToExecute": "'${SCRIPT_COMMAND}'"}'

	echo "Deploying to $REGION"

  	# Update the VMSS Model with a fileUrl including the build number
  	# This updated model will force all VM isntances to be out of compliance
  	az vmss extension set \
      --name            "CustomScript" \
      --publisher       Microsoft.Azure.Extensions \
      --resource-group  ${RG}${REGION} \
      --vmss-name       ${VMSS} \
      --settings        "${NEWSETTINGS}"
  
    # Trigger all VMs to update their model
    az vmss update-instances \
      --instance-ids 		"*" \
      --name 				${VMSS} \
      --resource-group 	    ${RG}${REGION}
      
      echo "Finished deploying to $REGION"
    
done
```

## Post-build Actions

None
