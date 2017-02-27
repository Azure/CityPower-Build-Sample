const fs = require('fs');
const path = require('path');
const express = require('express');
const router = express.Router();
const formidable = require('formidable');
const dataUtility = require('../utilities/data');

/* GET new outage */
router.get('/', (req, res) => {
    res.render('new', {
        title: 'Report an Outage'
    });
});

// POST new outage
router.post('/', (req, res) => {

    // Parse a form submission with formidable
    var form = new formidable.IncomingForm({ uploadDir: path.join(__dirname, '../public/img') });
    form.parse(req, (err, fields, files) => {

        // Process the fields into a new incident, and upload image
        dataUtility
            .createIncident(fields, files)
            .then(uploadImage)
            .then((incidentId) => {

                // Successfully processed form upload
                // Redirect to dashboard
                res.redirect(`/detail/${incidentId}`);

            });

    });

});

module.exports = router;

function uploadImage(input) {

    return new Promise((resolve, reject) => {

        // Check if no image was uploaded
        if (input[1].image.size === 0) {
            console.log('No image uploaded');
            resolve(input[0]);
        }
        else {

            // Use the storage utility to upload a blob to Azure Storage
            dataUtility.uploadIncidentImage(input).then((blob) => {
                console.log('Image uploaded');
                resolve(blob.id.toString());
            });

        }

    });

}