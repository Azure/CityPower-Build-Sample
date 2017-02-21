#!/bin/bash

export AZURE_STORAGE_ACCESS_KEY=<your storage key>
export AZURE_STORAGE_ACCOUNT=<your storage account>
export AZURE_STORAGE_BLOB_CONTAINER=images
export AZURE_STORAGE_QUEUE=thumbnails
export INCIDENT_API_URL=<your api url>
export INCIDENT_RESOURCE_PATH=/incidents
export REDISCACHE_HOSTNAME=<your cache hostname>
export REDISCACHE_PORT=6379
export REDISCACHE_PRIMARY_KEY=<your redis primarky key>
export REDISCACHE_SSLPORT=6380
export AAD_CLIENT_ID=<your AAD client id>
export AAD_CLIENT_SECRET=<your aad client secret>
export AAD_RETURN_URL=<your return URL>
export APPLICATION_INSIGHTS_IKEY=<your key>
gradle bootRun
