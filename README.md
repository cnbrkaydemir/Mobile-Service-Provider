# Mobile-Service-Provider

## General Overview
Package control system targeted for mobile service providers just like Verizon or T-Mobile. (Package in this context might also mean mobile plan)
The rest API provides users to view and manipulate different types of packages where each package represents a list of calls, SMS and cellular data also mentioned as credits.
Depending on the role of the users, they can manipulate packages, create new packages, assign credits to packages, view other users, and even delete packages.

## Technical Overview
* Spring framework: The project was built using the spring framework which means every functionality is somewhat dependent on the spring and the spring boot.
* DBMS: The database used in this project is PostgreSQL due to the open-source ideology.
* Security: Authentication and authorization constructed by JWT tokens built upon Oauth2 resource server(preferred over using custom security filters). 
* Cache: Redis cache is used for retrieval operations in order to improve the performance of the system. CachePut on repeated Get requests and CacheEvict on POST, Delete and Put requests.
* Kafka: Apache Kafka is used as a logger service for this project. Operations like user registration, authentication and assigning packages to users are represented as topics and specific messages are stored in the database. 

