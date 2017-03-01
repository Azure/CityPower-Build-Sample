module.exports = {
  /**
   * Application configuration section
   * http://pm2.keymetrics.io/docs/usage/application-declaration/
   */
  apps: [

    // API Tier
    {
      name: "CityPower.API",
      script: "api/app.js",
      env: {
        PORT: "8080",
        DB_HOST: "",
        DB_PORT: "",
        DB_DATABASE: "citypower",
        DB_USER: "",
        DB_PASSWORD: "",
        DB_SSL: "false"
      }
    },

    // Web Tier
    {
      name: "CityPower.Web",
      script: "web/app.js",
      env: {
        PORT: "80",
        API_URL: "http://localhost:8080/api",
        AZURE_STORAGE_ACCOUNT: "",
        AZURE_STORAGE_ACCESS_KEY: "",
        REDISCACHE_HOSTNAME: "",
        REDISCACHE_PRIMARY_KEY: "",
        REDISCACHE_SSLPORT: ""
      }
    }
  ]
}