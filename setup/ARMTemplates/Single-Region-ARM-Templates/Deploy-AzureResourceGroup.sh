#!/bin/bash

# Resource group name to create the storage account used to store the ARM templates.
# The storage account in this resource group is only used for deployments.
STG_ACCT_RSRC_GRP_NAME="ARM_Deploy_Staging"
RSRC_GRP_NAME_PREFIX="OpenDev-SingleRegion"

VALIDATE_ONLY=false

showErrorAndUsage() {
  echo
  if [[ "$1" != "" ]]
  then
    echo "  error:  $1"
    echo
  fi

  echo "  usage:  $(basename ${0}) [options]"
  echo "  options:"
  echo "    -a, --apptype             [Required] : Suffix to append to resource group name, such as 'Node' or 'Java''."
  echo "    -l, --location <location> [Required] : Location to deploy to."
  echo "    -v, --validate-only"
  exit 1
}

if [[ $# < 4 ]]
then
  showErrorAndUsage
fi

while [[ $# > 0 ]]
do
  key="$1"
  case $key in
    -a|--apptype)
      APP_TYPE="$2"
      shift
      ;;
    -l|--location)
      LOCATION="$2"
      shift
      ;;
    -v|--validate-only)
      VALIDATE_ONLY=true
      shift
      ;;
    *)
      showErrorAndUsage "Unknown option: $1"
    ;;
  esac
  shift
done

# Get the Azure Subscription Id 
SUBSCRIPTION_ID=$( az account show --query id )
SUBSCRIPTION_ID=${SUBSCRIPTION_ID//[\"]/}

# Create the resource group for the storage account if it does not already exist.
STG_ACCT_RSRC_GRP_EXISTS=$( az group exists --name ${STG_ACCT_RSRC_GRP_NAME} )
if [[ $STG_ACCT_RSRC_GRP_EXISTS == "false" ]]
then
    echo "Creating resource group '$STG_ACCT_RSRC_GRP_NAME'."
    az group create --location $LOCATION --name $STG_ACCT_RSRC_GRP_NAME
fi

# Create a storage account name to upload the artifacts to.
STG_ACCT_NAME=${SUBSCRIPTION_ID//[-]/}
STG_ACCT_NAME=$( expr substr $STG_ACCT_NAME 1 19 )
STG_ACCT_NAME="stage${STG_ACCT_NAME}"

# Create a container name to hold the artifacts.
RSRC_GRP_NAME="${RSRC_GRP_NAME_PREFIX}-${APP_TYPE}"
STG_CONTAINER_NAME="${RSRC_GRP_NAME,,}-stageartifacts"

# Create the storage account if it doesn't already exist.
STG_ACCT_NAME_AVAIL=$( az storage account check-name --name $STG_ACCT_NAME --output tsv | cut -f2) 
STG_ACCT_NAME_AVAIL=${STG_ACCT_NAME_AVAIL,,}
if [[ $STG_ACCT_NAME_AVAIL == "true" ]]
then
    echo "Storage account '$STG_ACCT_NAME' does not exist.  Creating in resource group '$STG_ACCT_RSRC_GRP_NAME'."
    az storage account create --location $LOCATION --name $STG_ACCT_NAME \
        --resource-group $STG_ACCT_RSRC_GRP_NAME --sku Standard_LRS
    echo "Creating container '$STG_CONTAINER_NAME' in storage account '$STG_ACCT_NAME'."
    az storage container create --name $STG_CONTAINER_NAME --account-name $STG_ACCT_NAME
else
    STG_CONTAINER_EXISTS=$( az storage container exists --name $STG_CONTAINER_NAME \
            --account-name $STG_ACCT_NAME --output tsv )
    if [[ ${STG_CONTAINER_EXISTS,,} == "false" ]]
    then
        echo "Creating container '$STG_CONTAINER_NAME' in storage account '$STG_ACCT_NAME'."
        az storage container create --name $STG_CONTAINER_NAME --account-name $STG_ACCT_NAME  
    fi
fi

# Get the key for the storage account we will upload artifacts to.
STG_ACCT_KEYS=$( az storage account keys list --account-name $STG_ACCT_NAME \
        --resource-group $STG_ACCT_RSRC_GRP_NAME --output tsv | cut -f3 )
IFS=' ' read -a STG_ACCT_KEYS <<< "${STG_ACCT_KEYS}"
# Using secondary key because of a special char that get's added to the primary key.
# Don't have time to figure out bash magic to remove it.  This works just was well.'
STG_ACCT_KEY=${STG_ACCT_KEYS[1]}  

# Upload files/artifacts to storage account.
ARTIFACT_STAGING_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
find -P $ARTIFACT_STAGING_DIR -type f |
while read artifact_file
do
    blob_name=${artifact_file: (${#ARTIFACT_STAGING_DIR} + 1)}
    echo "Uploading $blob_name ..."
    az storage blob upload -f $artifact_file -c $STG_CONTAINER_NAME -n $blob_name \
        --account-name $STG_ACCT_NAME --account-key "${STG_ACCT_KEY}"
done

# Generate a SAS token for the storage container the artifacts were uploaded to
SAS_EXPIRY=$( date -d "+4 hours" +%Y-%m-%dT%TZ )
SAS_TOKEN=$( az storage container generate-sas --name $STG_CONTAINER_NAME --permissions r --account-name $STG_ACCT_NAME --expiry $SAS_EXPIRY )

# Create the resource group for the deployment.
# echo "Creating resource group '$RSRC_GRP_NAME' in '$LOCATION'."
# az group create --name $RSRC_GRP_NAME --location $LOCATION

if [[ $VALIDATE_ONLY == true ]]
then
    echo "Validating "
else
    echo "Deploying "
fi

