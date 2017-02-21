const express = require('express');
const router = express.Router();
const dataUtility = require('../utilities/data');

/* GET dashboard. */
router.get('/', (req, res) => {

    // Determine dashboard page
    var page = null;
    if (req.query.page) { page = req.query.page; }
    else { page = "1"; };

    dataUtility.getIncidents(page).then((incidents) => {

        // Render view
        res.render('dashboard', {
            title: 'Outage Dashboard',
            incidents: incidents,
            page: page
        });

    });

});

module.exports = router;