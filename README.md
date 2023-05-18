# Project: CryptocurrencyWatcher

### Technologies
- Spring boot: 2.7.11 (dependency management: 1.1.0)
- Spring (Data JPA, MVC)
- H2 database
- ModelMapper: 3.1.1
- Lombok

#### Server port 8080
- Command: localhost:8080/api/crypto - Show all supported coins (BTC, ETH, SOL)
- Command: localhost:8080/api/crypto/[symbol] - Show information about interested coin by symbol
- Command: localhost:8080/api/users - Show all information about users and their selected coins

#### Register new user by console: 
##### Enter command in console: curl -d "username=[username]&symbol=[symbol]" http://localhost:8080/api/crypto/notify
##### Fill in the values [username] and [symbol]. 
##### You can check new user by: localhost:8080/api/users or localhost:8080/api/users/{id} start with 2 (first one is test user)