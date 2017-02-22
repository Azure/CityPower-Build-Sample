const express = require('express');
const expressSession = require('express-session');
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');
const path = require('path');
const pug = require('pug');

module.exports.setup = (app) => {

    // Setup Express Middleware
    app.use(expressSession({ secret: 'citypower', resave: true, saveUninitialized: false }));
    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({ extended: true }));
    app.use(cookieParser());
    app.use(express.static(path.join(__dirname, '../public')));

    // Setup View Engine
    app.set('views', path.join(__dirname, '../views'));
    app.set('view engine', 'pug');

};