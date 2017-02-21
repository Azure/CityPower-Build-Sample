curl -i -X PATCH -H "Content-Type:application/json" -d "{  \
     \"Description\" : \"Description\", \
     \"Street\" : \"street\" ,  \
     \"City\" : \"city\" ,  \
     \"State\" : \"state\" ,  \
     \"ZipCode\" : \"zipcode\" ,  \
     \"FirstName\" : \"firstname\" ,  \
     \"LastName\" : \"lastname\" ,  \
     \"PhoneNumber\" : \"515-555-1234\" ,  \
     \"OutageType\" : \"outagetype\" ,  \
     \"IsEmergency\" : \"false\" ,  \
     \"ImageUri\" : \"foobarbaz.jpg\" , \
     \"Resolved\" : \"false\" }" \
     http://localhost:9000/incidents/58a486451c4f588b3e2ef12c
