const express = require('express');
const router = express.Router();
const request = require('request');

/* GET dashboard. */
router.get('/:id', (req, res) => {

    getIncident(req.params.id).then((incident) => {

        // Render view
        res.render('detail', {
            title: 'Detail',
            incident: incident
        });

    });

});

module.exports = router;

function getIncident(id) {

    // Define URL to use for the API
    var apiUrl = `${process.env.API_URL}/incidents/${id}`;

    return new Promise((resolve, reject) => {

        // Make a GET request with the Request libary
        request(apiUrl, { json: true }, (error, results, body) => {

            // Resolve Promise with incident data
            resolve(body);

        });

    });

}