#!/bin/bash

# Resource group name to create the storage account used to store the ARM templates.
# The storage account in this resource group is only used for deployments.
STG_ACCT_RSRC_GRP_NAME="ARM_Deploy_Staging"

showErrorAndUsage() {
  echo
  if [[ "$1" != "" ]]
  then
    echo "  error:  $1"
    echo
  fi

  echo "  usage:  $(basename ${0}) [options]"
  echo "  options:"
  echo "    -a, --apptype [ Node | Java ]"
  echo "    -l, --location <location>"
  echo
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
    *)
      showErrorAndUsage "Unknown option: $1"
    ;;
  esac
  shift
done

# Get the Azure Subscript Id 
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
echo $STG_ACCT_NAME

# Create the storage account if it doesn't already exist.
STG_ACCT_NAME_AVAIL=$( az storage account check-name --name $STG_ACCT_NAME --out tsv | cut -f2) 
STG_ACCT_NAME_AVAIL=${STG_ACCT_NAME_AVAIL,,}
if [[ $STG_ACCT_NAME_AVAIL == "true" ]]
then
    echo "Storage account '$STG_ACCT_NAME' does not exist.  Creating in resource group '$STG_ACCT_RSRC_GRP_NAME'."
    az storage account create --location $LOCATION --name $STG_ACCT_NAME --resource-group $STG_ACCT_RSRC_GRP_NAME --sku Standard_LRS
fi




