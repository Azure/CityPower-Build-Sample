module.exports = {
  /**
   * Application configuration section
   * http://pm2.keymetrics.io/docs/usage/application-declaration/
   */
  apps : [

    // First application
    {
      name      : "CityPower.API",
      script    : "api/app.js",
      env: {
        PORT: "8080",
	DB_HOST: "cp2.documents.azure.com",
	DB_PORT: "10250",
	DB_DATABASE: "citypower",
	DB_USER: "cp2",
	DB_PASSWORD: "Atay20cEBj7ObNq1j7IfybFqei4lzFxuwPqJfgRL9ufnJm0XbOGI1U4Aee0N9jyEonFhfzwsesX1JUfSMNTvKw==",
	DB_SSL: "true"
      }
    },

    // Second application
    {
      name      : "CityPower.Web",
      script    : "web/app.js",
      env: {
      	PORT: "80",
	API_URL: "http://localhost:8080/api",
	REDISCACHE_HOSTNAME: "cp2.redis.cache.windows.net",
	REDISCACHE_PRIMARY_KEY: "hbl5OoSaFosHIsJgIa2qXywZFjHcn0xxk6g6CaYbKs8=",
	REDISCACHE_SSLPORT: "6380",	
	AZURE_STORAGE_ACCOUNT: "cp2storage",
	AZURE_STORAGE_ACCESS_KEY: "KVNTJMq+JDsf9mdmpHZe6NPeu5cnEPrV0Jo2Gw6BinOotu4fTW/cWDAGy+kDLZ5yiCxT0RYcjAPLW8rnRd4XyQ=="
      }
    }
  ]
}
