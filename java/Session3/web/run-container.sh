#!/usr/bin/env bash

docker run -itp:8080:8083 \
    -e AZURE_STORAGE_ACCESS_KEY="yMH9b0E3u5Cf9sZTwxwoQXsM90ygX/VO5qyJmRStJ1NR5cr8xiLm788ELahxTtzL376BGJZG8SOdx5PnlZpkbA==" \
    -e AZURE_STORAGE_ACCOUNT="incidentblobstg32csxy6h3" \
    -e AZURE_STORAGE_BLOB_CONTAINER="images" \
    -e AZURE_STORAGE_QUEUE="thumbnails" \
    -e INCIDENT_API_URL="http://incidentapi32csxy6h3sbku.azurewebsites.net" \
    -e INCIDENT_RESOURCE_PATH="/incidents" \
    -e REDISCACHE_HOSTNAME="incidentcache32csxy6h3sbku.redis.cache.windows.net" \
    -e REDISCACHE_PORT=6379 \
    -e REDISCACHE_PRIMARY_KEY="Fw1IK6VXPILlqXNOiUBd9Kl8nyWLLBvR2QwosWZkRLY=" \
    -e REDISCACHE_SSLPORT=6380 \
    azurecat-gsi/javaapp
