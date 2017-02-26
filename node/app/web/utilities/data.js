const fs = require('fs');
const path = require('path');
const mime = require('mime');
const request = require('request');

// Setup Azure Blob Storage services
if (process.env.AZURE_STORAGE_ACCOUNT) {
    const azure = require('azure');
    var blobService = azure.createBlobService();
    var useCloudStorage = true;
}

// Setup Redis Client
if (process.env.REDISCACHE_HOSTNAME) {
    var redis = require("redis");
    var redisClient = redis.createClient(process.env.REDISCACHE_SSLPORT, process.env.REDISCACHE_HOSTNAME, {
        auth_pass: process.env.REDISCACHE_PRIMARY_KEY,
        tls: {
            servername: process.env.REDISCACHE_HOSTNAME
        }
    });
    var useCache = true;
};

module.exports = {

    getIncidents: (page) => {

        return new Promise((resolve, reject) => {

            // Check if caching is turned on
            if (useCache) {
                // Use Redis Cache
                getIncidientsCache(page).then((incidents) => {
                    resolve(incidents);
                });
            } else {
                // Do not use Redis Cache
                getIncidientsNoCache(page).then((incidents) => {
                    resolve(incidents);
                });
            }

        });

    },

    createIncident: (fields, files) => {

        return new Promise((resolve, reject) => {

            // Set API URL from environment variable
            var apiUrl = `${process.env.API_URL}/incidents`;

            // POST new incident to API
            request.post(apiUrl, {
                form: fields,
                json: true
            }, (error, results) => {

                // Successfully created a new incident
                console.log('Created incident');

                // If cache is on, bust the cache for the new item
                if (useCache) {
                    redisClient.del(`page1`, function (error, reply) {
                        var incidentId = results.body.id;
                        resolve([incidentId, files]);
                    });
                } else {
                    var incidentId = results.body.id;
                    resolve([incidentId, files]);
                }

            });

        });

    },

    uploadIncidentImage: (input) => {

        return new Promise((resolve, reject) => {

            if (useCloudStorage) {
                uploadIncidentImageCloud(input).then((body) => {
                    resolve(body);
                });
            } else {
                uploadIncidentImageLocal(input).then((body) => {
                    resolve(body);
                });
            }

        });

    },

    uploadIncidentImageLocal: (input) => {

    }

};

function uploadIncidentImageLocal(input) {

    return new Promise((resolve, reject) => {

        // Define variables to use with the Blob Service
        var directory = input[1].image.path.split('/').slice(0, -1).join('/');
        var blobName = input[0] + '.' + mime.extension(input[1].image.type);

        fs.rename(input[1].image.path, directory + '/' + blobName, (err, result) => {

            // Successfully uploaded the image
            console.log(`Uploaded file to Local Disk`);

            // Store imageuri in the DB
            updateIncidentImage(blobName).then((body) => {
                resolve(body);
            });

        });

    });

}

function uploadIncidentImageCloud(input) {

    return new Promise((resolve, reject) => {

        // Define variables to use with the Blob Service
        var stream = fs.createReadStream(input[1].image.path);
        var streamLength = input[1].image.size;
        var options = {
            contentSettings: {
                contentType: input[1].image.type
            }
        };
        var blobName = input[0] + '.' + mime.extension(input[1].image.type);
        var blobContainerName = `images`;

        // Confirm blob container
        blobService.createContainerIfNotExists(blobContainerName, {
            publicAccessLevel: 'container'
        }, (containerError) => {

            // Upload new blob
            blobService.createBlockBlobFromStream(blobContainerName, blobName, stream, streamLength, options, (blobError, blob) => {

                // Successfully uploaded the image
                console.log(`Uploaded file to Azure Blob Storage`);

                updateIncidentImage(blob.name).then((body) => {
                    resolve(body);
                });

            });

        });

    });

}

function updateIncidentImage(image) {

    return new Promise((resolve, reject) => {

        // Parse image name to get incident ID
        var incidentId = image.split('.')[0];

        // Set API URL from environment variable
        var apiUrl = `${process.env.API_URL}/incidents/${incidentId}`;

        // GET Incident by incident
        request.get(apiUrl, {
            json: true
        }, (error, response, body) => {

            // Update incident
            var updatedIncident = body;
            updatedIncident.imageuri = image;

            // POST updated incident to API
            request.put(apiUrl, {
                form: updatedIncident,
                json: true
            }, (error, results, body) => {

                // Successfully created a new incident
                console.log('Updated incident data with imageuri');

                resolve(body);

            });

        });

    });

}

function getIncidientsCache(page) {

    return new Promise((resolve, reject) => {

        // Check cache for incidentData key
        redisClient.get(`page${page}`, function (error, result) {

            if (result) {

                // Cache key exists
                console.log(`Cached key found for page${page}`);

                resolve(JSON.parse(result));

            } else {

                // Cache key does not exists - query API
                getIncidientsNoCache(page).then((result) => {

                    // Store results in cache
                    redisClient.set(`page${page}`, JSON.stringify(result), 'EX', 30, function (error, reply) {

                        console.log(`Stored results in cache for page${page}`);

                        resolve(result);

                    });

                });
            }

        });

    });

}

function getIncidientsNoCache(page) {

    // Define URL to use for the API
    var apiUrl = `${process.env.API_URL}/incidents?filter[order]=id desc&filter[limit]=9&filter[skip]=${(page - 1) * 9}`;

    return new Promise((resolve, reject) => {

        // Make a GET request with the Request libary
        request(apiUrl, {
            json: true
        }, (error, results, body) => {

            // Resolve Promise with incident data
            resolve(body);

        });

    });

}