# NodeMultiRegionApi

In a Jenkins server, [create](https://wiki.jenkins-ci.org/display/JENKINS/Building+a+software+project#Buildingasoftwareproject-Settinguptheproject) a new Free Style Project named **NodeMultiRegionApi**. 

The following sections correspond to the new project, and contain configuration values.

## General

* Project name: NodeMultiRegionApi

* Description: Signs into the Azure CLI with a Service Principal and adds a Custom Script Extension to a set of Virtual Machine Scale Sets located in multiple regions. The Custom Script Extension is created with arguments corresponding to the machine's desired enviroment variables.

* GitHub Project URL: None

## Source Code Management

None

## Build Triggers

To enable automatic releases for the API when a build is done, check the box next to **Build after other projects are built** and select **NodeBuildApi**.

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

# Target Virtual Machine Scale Set
VMSS="bizVM"

# Resource Group Containing the target VMSS
RG="OpenDev-MultiRegion-Node-"
RGREGIONS="westus,eastus"

# Environment Variables for application
BUILD_ARTIFACT="https://opendevjenkinsartifacts.blob.core.windows.net/nodebuildapi/archive.zip"

DB_HOST="docdb-azurex-z4jsvrtij7bry.documents.azure.com"

DB_PORT="10250"

DB_DATABASE="citypower"

DB_USER="docdb-azurex-z4jsvrtij7bry"

DB_PASSWORD="d2Cvyf38TI4MIRHBHvjGyCmrs4eyF5QTPtcMOC8SjA34nqGgszZxjYSHxIVz9bJzlI3ZelEs51IgkxcyraH4Ug=="

DB_SSL="true"

# Extension Script URL
SCRIPT="https://opendevjenkinsartifacts.blob.core.windows.net/scripts/setup/Extensions/node/SetupApiVMSS.sh"

# Script to Execute
SCRIPT_COMMAND='sh SetupApiVMSS.sh '${BUILD_ARTIFACT}' \"'${DB_HOST}'\" \"'${DB_PORT}'\" \"'${DB_DATABASE}'\" \"'${DB_USER}'\" \"'${DB_PASSWORD}'\" \"'${DB_SSL}'\"'

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

NEWSETTINGS='{"fileUris": ["'${SCRIPT}'?build='${BUILD_NUMBER}'"], "commandToExecute": "'${SCRIPT_COMMAND}'"}'

# Deploy to each region
echo "Starting deploying to regions - $RGREGIONS"
for REGION in $(echo $RGREGIONS | sed "s/,/ /g")
do

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
      --resource-group 	${RG}${REGION}
      
      echo "Finished deploying to $REGION"
    
done
```

## Post-build Actions

None
