curl -i -X POST -H "Content-Type:application/json" -d "{ \
      \"Description\": \"Aliquam vulputate ullamcorper magna. Sed eu eros. Nam consequat\", \
      \"Street\": \"190-4644 Est Avenue\", \
      \"City\": \"Suwałki\", \
      \"State\": \"PD\", \
      \"ZipCode\": \"14707\", \
      \"FirstName\": \"Colette\", \
      \"LastName\": \"Atkinson\", \
      \"PhoneNumber\": \"(725) 564-3651\", \
      \"OutageType\": \"Gas leak\", \
      \"IsEmergency\": \"false\", \
      \"Resolved\": \"false\", \
      \"Created\": \"2016-09-26T12:37:14-07:00\", \
      \"LastModified\": \"2016-08-21T06:42:59-07:00\" }" \
     http://localhost:9000/incidents
