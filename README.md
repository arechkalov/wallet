 

# Wallet Service


Service consists of a wallet server and a wallet client connected via gRPC; 
The wallet server will keep track of a users monetary balance in the system. 
The client will emulate users depositing and withdrawing funds.

database schema and diagram for the server is included into the root directory.

Technologies used:

*  Java 8
*  Spring Boot 2
*  gRPC
*  Hibernate
*  MySQL
*  Gradle
*  JUnit


---

## client

The wallet client will emulate a number of users concurrently using the wallet server. 
The wallet client connected to the wallet server over gRPC. 
The client eliminating users doing rounds (a sequence of events) concurrently. Whenever a round is needed it is picked at random from the following list of available rounds
Client will exit when all rounds are done

Round A
* Deposit 100 USD
* Withdraw 200 USD
* Deposit 100 EUR
* Get Balance
* Withdraw 100 USD
* Get Balance
* Withdraw 100 USD

Round B
* Withdraw 100 GBP
* Deposit 300 GPB
* Withdraw 100 GBP
* Withdraw 100 GBP
* Withdraw 100 GBP

Round C
* Get Balance
* Deposit 100 USD
* Deposit 100 USD
* Withdraw 100 USD
* Depsoit 100 USD
* Get Balance
* Withdraw 200 USD
* Get Balance

When round is needed it is picked at random from the following list of available rounds

#### Default client properties

```
betpawa.client.number-of-users=100
betpawa.client.number-of-threads=10
betpawa.client.number-of-rounds=10
```
* number-of-users - number of concurrent users emulated
* number-of-threads - number of concurrent requests a user will make
* number-of-rounds - number of rounds each thread is executing

  
## server

*  Saves data from user's requests to database
*  Retrieves balance amount from wallet for userId and currency

### server properties
```
number.of.users=100
```

## proto
    
*  Holds proto file for generation    
*  Generates gRPC proto models and service stubs. 

### How to run:
     1. Execute schema.sql on local mySQL server.
      
     2. SERVER:
        * Adjust properties for database connection (by default it is connected to local server):
            spring.datasource.url=jdbc:mysql://localhost:3306/bet_pawa?useSSL=false
            spring.datasource.username=root
            spring.datasource.password=welcome1
        * Adjust number.of.users property in application.properties for server (by default server will start with 100 of users.
        * Start server via run-server.bat
     
     3. CLIENT: 
        * Adjust client.properties and setup number of users, rounds and threads.
        * Start client via run-client.bat
---

### Explanation of important choices:
    * Sql schema was created with balance per user and per currency as dictionary table with USD, GBP and EUR values inserted
    * Retry mechanism is not implemented, though Optimistick locking is applied on balance
    * Common request was made for all calls, empty responses used for deposit and withdraw operations.
    * Logging added for tracing requests
    * Configured number.of.users (default is 100) to start server with empty balances for each.
    * Added integration test on server side with in-mem H2 database.
    * Client exists when all requests are done.
---

