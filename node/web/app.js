// Modules
const express = require('express');
const experssHelper = require('./utilities/express');
const errorHelper = require('./utilities/errors');
const appInsightsHelper = require('./utilities/appInsights');

// Create Express Application
var app = express();

// Configure Application Insights
appInsightsHelper.setup(app);

// Configure Locals
var env = process.env.NODE_ENV || 'development';
app.locals.moment = require('moment');

// Configure Express 
experssHelper.setup(app);

// Configure Routes
app.use('/', require('./routes/index'));
app.use('/dashboard', require('./routes/dashboard'));
app.use('/detail', require('./routes/detail'));
app.use('/new', require('./routes/new'));

// Configure Errors
errorHelper.setup(app);

// Start Server
app.set('port', process.env.PORT || 3000);
var server = app.listen(app.get('port'), () => {
    console.log('Express server listening on port ' + server.address().port);
});