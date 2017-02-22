const express = require('express');
const router = express.Router();

/* GET home page. */
router.get('/', (req, res) => {

    // Render view
    res.render('index', {
        title: 'City Power & Light',
        home: true
    });

});

module.exports = router;