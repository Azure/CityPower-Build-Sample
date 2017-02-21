var appInsights = require("applicationinsights");

module.exports.setup = function (app) {

    // Check if an instrumentation key is configured
    if (process.env.APPINSIGHTS_INSTRUMENTATIONKEY) {

        console.log('App Insights Key Found. Starting AI');

        // Setup the Application Insights client
        // .setup() can be called without an instrumentation key
        // when an environment variable is set
        appInsights.setup().start();

    }

}