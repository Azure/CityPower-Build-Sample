"use strict";

// Check environment variable for SSL and return true or false
var sslSetting = function () {
  if (process.env.DB_SSL === "true") {
    return true;
  } else {
    return false;
  }
};

module.exports = {
  "DocDB": {
    "host": process.env.DB_HOST,
    "port": process.env.DB_PORT,
    "database": process.env.DB_DATABASE,
    "user": process.env.DB_USER,
    "password": process.env.DB_PASSWORD,
    "ssl": sslSetting()
  },
};
