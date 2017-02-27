// Check if App Insights has been initialized with a key
if (appInsights.config.instrumentationKey.length > 0) {

    console.log(`AI Instrumentation Key found. Creating click event handlers`);

    // Add a click event handler for the Dashboard button
    $('.btn-click-dashboard').click(function () {
        appInsights.trackEvent('Home.Click.Dashboard');
        console.log(`Logged click to Dashboard Button`);
    });

    // Add a click event handler for the Report button
    $('.btn-click-report').click(function () {
        appInsights.trackEvent('Home.Click.Report');
        console.log(`Logged click to Report Button`);
    });

} else {
    console.log(`No AI Instrumentation Key found.`);
}