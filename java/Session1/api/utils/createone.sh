curl -i -X POST -H "Content-Type:application/json" -d "{  \
     \"Description\" : \"Description\", \
     \"Street\" : \"street\" ,  \
     \"City\" : \"city\" ,  \
     \"State\" : \"state\" ,  \
     \"ZipCode\" : \"zipcode\" ,  \
     \"FirstName\" : \"firstname\" ,  \
     \"LastName\" : \"lastname\" ,  \
     \"PhoneNumber\" : \"515-555-1212\" ,  \
     \"OutageType\" : \"outagetype\" ,  \
     \"IsEmergency\" : \"false\" ,  \
     \"Created\" : \"2/15/17@8:35pm\" , \
     \"Resolved\" : \"false\" }" \
     http://localhost:9000/incidents
