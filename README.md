# Customer Transactions Service API
Microservice APIs to create and manage customer transactions

### Prerequisites:
* Java 17
* Docker
* Maven
* Postman
* PostgreSQL DB client - pgAdmin 4

### Run instructions

1. Clone transactions-service repository into local workspace
2. Start Docker container to run PostgreSQL DB instance with command: docker-compose -f docker-compose.yaml up
3. Start Spring Boot application with command: mvn spring-boot:run (application should be started and listening under port 8080)
4. Connect to PostgreSQL DB instance using pgAdmin. DB=transactions, Schema=transactions, username=demo, password=demo
5. Open ./transactions-service.postman_collection.json in Postman
6. Verify application health via /transactions-service/actuator/health endpoint

### API Security

**Note**: API endpoints are authenticated and authorised using JWT tokens as below.

API clients should fetch the JWT token with successful authentication and authorisation claims via below endpoint:
POST /transactions-service/auth/login

Authentication is performed by matching the email and password in auth request with user credentials maintained the PostgresSQL DB.
Password is stored in DB in **encoded format** using Spring Security BCryptPasswordEncoder.

For authenticated users, the server will respond with a JWT token containing user roles and other claims.
Any API client requests to create new transaction or fetch reporting data should send a valid JWT Token with respective user role in the HTTP **Auhorization** header with '**Bearer** ' prefix.
Any requests with invalid tokens will be rejected with a **HTTP 403 forbidden error.**


### Verify functionality

POST /transactions-service/transaction: Create transaction endpoint
   -- authenticated with user email and password
   -- authorised only for users with '**TX_CUSTOMER**' role
   -- should return success response with transaction id
   -- should return bad request exception for any invalid transaction requests

GET /transactions-service/reporting/transactions/cost: Reporting endpoint for transactions cost by customer or product
   -- authenticated with user email and password
   -- authorised only for users with **'TX_ADMIN' or 'TX_REPORTING'** role
   -- flexible API to fetch transactions cost for all customers and products or per customer or per product.

GET /transactions-service/reporting/transactions/volume: Reporting endpoint for transactions volume by customer location
   -- authenticated with user email and password
   -- authorised only for users with **'TX_ADMIN' or 'TX_REPORTING'** role


**Note**: The functional API endpoints allow customer HTTP header Accept: "**application/x-kryo**" introduced as an option 
to send and receive API request and response data in a binary format for better performance 
considering the compact size and reduced serialization and de-serialization overheads.




