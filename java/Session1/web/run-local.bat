#!/bin/bash
set AZURE_STORAGE_ACCESS_KEY=<your storage key>
set AZURE_STORAGE_ACCOUNT=<your storage account>
set AZURE_STORAGE_BLOB_CONTAINER=images
set AZURE_STORAGE_QUEUE=thumbnails
set INCIDENT_API_URL=<your api url>
set INCIDENT_RESOURCE_PATH=/incidents
set REDISCACHE_HOSTNAME=<your cache hostname>
set REDISCACHE_PORT=6379
set REDISCACHE_PRIMARY_KEY=<your redis primarky key>
set REDISCACHE_SSLPORT=6380
set AAD_CLIENT_ID=<your AAD client id>
set AAD_CLIENT_SECRET=<your aad client secret>
set AAD_RETURN_URL=<your return URL>
set APPLICATION_INSIGHTS_IKEY=<your key>
gradle bootRun
