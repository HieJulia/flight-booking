# flight-booking
Online flight reservation system 

+ User service : operations on User service 
+ Booking service : Flight service and User service to perform operations on booking. It will use flight search and its associated tabl
+ Flight service : operations and searching based on criteria, providing association between Flight and flight trip 
+ Billing service : operations on billing 



+ 


### Stack 
+ Microservice architecture : Spring cloud, Netflix Eureka, Ribbon, Zuul, Hystrix, Service discovery, Load Balancing, API gateway, Circuit breaker (Hystrix)
+ Spring framework : Spring Boot, Spring cloud, Spring data, Spring Stream , Spring Reactor
+ CQRS - Event sourcing 
+ Database : PostgreSQL, MongoDB, Cassandra, MySQL, Neo4J , MySQL
+ Caching : Redis
+ Messaging system : RabbitMQ 
+ Batch process 
+ Apache Avro
+ ElasticSearch - Logstash - Kibana 
+ Container: Docker  - Docker compose 
+ REST API testing using Postman
+ Testing : JUnit, E2E test with Cucumber
+ Event - driven system 
+ Security : OAuth/ JWT 
+ Log analysis : ELK stack
+ API doc : Swagger 
+ DevOps 
+ Service Logging / Monitoring 



### Endpoint documentation 
+ Flight service
    + GET `v1/flights/id` : get one flight by id / info 
    + GET `v1/flights` : retrieve all the flights that matches the value of query param 
    + POST `v1/flights` : create new flight 
    + GET `v1/airports` : get a list of airports
    + GET `v1/airports/{airport-name}` : list of flights from this airport 
    + Search flight by name
    

+ Booking service : User can book a flight ticket and fill the personal information - billing information 
    + POST `v1/flights/{flight-id}/booking ` : 
    + Get booking details 

+ Billing service : User can pay the flight order 
    + GET `v1/flights/{flight-id}/payment`
    + Make payment(handle payment errors: payment authorization timeout and invalid credit card info )

+ User can unsubscribe to the flight ticket info
    + GET `v1/flights/{flight-id}/payment/ubsubscribed`




### Run the project 

+ Run everything : 



+ `docker-compose up` : RabbitMQ port set up (in the docker folder) : it will start the RabbitMQ and MongoDB instance 

java -jar eureka-server/target/eureka-server.jar  : Start Eureke server  -  Eureka server port set up
java -jar dashboard-server/target/dashboard-server.jar : Start Hystrix dashboard - Hystrix port set up 
java -jar turbine-server/target/turbine-server.jar 

java -jar flight-service/target/restaurant-service.jar 
java -jar user-service/target/user-service.jar 
java -jar booking-service/target/booking-service.jar 
java -jar api-service/target/api-service.jar

+ Before start Zuul service, make sure that all of the services are up in the Eureka dashboad : `localhost:8761`
java -jar zuul-server/target/zuul-server.jar 



+ How to double check all components are setting up 
1. Browser : Eureka server 
`http://localhost:8761/`
Service instances are register with Eureka 


2. Browser : Hystrix monitor : `http://localhost:7979/ ` - Hystrix dashboard 
+ Substitude : [http://hostname:port/turbine/turbine.stream] to
                    URL:  http://localhost:9001/hystrix.stream
         --> Monitor Stream

3. Browser : RabbitMQ management : `http://localhost:15672/` (guest- guest )





### Microservice architecture 
+ Service discovery and registration - Eureka 
    + Run multiple instances of the same microservices
    + Look up the host name and the port number by checking discovery service 
    + Eureka server : `http://localhost:8761/`
+ Config service   
    + Application configuration to all the other microservices 
    + Port number, context paths 
    + Configuration - using GIT 

+ Edge or proxy server (API gateway) - Zuul - Gateway service 
    + Gateway service Zuul 
    + Proxy all calls to the target microservice 
    + Solve CORS request - Enable CORS headers on the proxy only
    + Zuul integrates with Eureka (discovery-service)
+ Load balancing : Ribbon is used for load balancing . It is integrated with the Zuul and Eureka services to provide load balancing for both internal and external calls 
    + Server side load balancing : Zuul server as edge server 
    + Client side load balancing : Ribbon - FeignClient 
+ Circuit breaker : Netflix hystrix 
+ Distributed tracing : Zipkin, Spring Cloud Sleuth 
+ Monitoring : Netflix Turbine  - and Dashboard 
    + Hystrix provides a dashboard UI `locahost:7979`
    + Turbine stream `http://localhost:8989/turbine.stream`
    + Hystrix uses RabbitMQ to send metrics data feed to Turbine 
+ UUA servvice 
    + User account & Authentication - security of the application 
    + /token endpoint to retrieve a token 
    + /user endpoint to validate a token and retrieve the user and its roles
    + Token in this case are long-lived 

+ Integration
+ Batch processing 
+ Security service 
    + Secure microservices architecture 
    + SSL enabled 

+ Dependency management : Maven 
+ Data Lake 
+ Testing 
+ Event sourcing 
+ HTTP listener 
+ Containers / Virtual Machines 
+ Cluster Control and Provisioning 

+ Admin server 

+ Spring cloud DataFlow server 


+ Microservice service design : contract design and protocol selection 
    + Contract design 
        + Simplicity - consumer 
        + KISS 
        + Consumer driven contract 
    + Protocol selection 
        + HTTP/ SOAP 
        + Messaging 
    + Message oriented service 
        + JMS / AMQP protocol - JSON 
        + Messaging over HTTP 
        + Async REST 
    + HTTP and REST endpoint 
        + Protocol handling 
        + Traffic routing 
        + Load balancing 
        + Security systems 
        + HATEOAS
    + Optimize communication protocol - for communication between service 
        + Avro 
        + Protocol Buffers 
        + Apache Thrift 
        + Custom binary protocol 
        + RPC 
+ API documentation : Swagger, RAML, API blueprint 








### Project architecture
+ Flight service    
    + Database : MongoDB 
    + User search for flight based on search query 
+ Catalog service 

+ User service - Account service 
    + User/ Customer register account
    + Database : MySQL 
+ Account service 
+ Booking service 
    + Database : MongoDB 
    + User book the flight and fill needed information for the flight 
+ Billing service 
    + Dabase : RDBMS 
    + User pay for flight ticket 

+ Subscription service 
    + Use
+ Route service 
    + User search for flight by flight route and flight city
+ Notification service 
    + Notification is send to user when user book a flight 
+ Ticker order service : before book for ticker, user can choose to order (reserve the flight ticker in a certain of time )
+ Payment service : After sending bill to user(customer), payment service is used for pay the fee of the flight ticket 
+ third party payment service 

+ Credit risk engine :  
    + Service to test for the validity of the bank account 
    










### Other 
#### How to deploy in AWS 
+ Create folder for each microservice(except flightsearchclient) project in AWS AMI root folder(e.g "eureka")
+ Copy the corresponding jar and the dockerfile in that folder (e.g "eurekaserver-0.0.1-SNAPSHOT.jar" and "Dockerfile" )
+ Go to that directory from root and run docker build command to create docker image("cd eureka","sudo docker build -t eureka .")
+ Go to root directory and run docker compose command("sudo docker-compose up")
+ To stop run "sudo docker-compose down"










