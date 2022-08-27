#Crypto-Tracker

Crpto-tracker service  Periodically monitor(once every 30 seconds) the price of Bitcoin(BTC) in USD from CoinGecko.
Also sends alerts when ever price above/ below the MIN and MAX vaue of price set though environment variable in docker-compose.yml 

### API:

| Method   | Endpoint                             | Description                                                                                         |
|----------|--------------------------------------|-----------------------------------------------------------------------------------------------------|
| GET      | /api/price/btc                       | Returns  pricing information of BITCOIN                                                                   |


##### Example:
To load the BITCOIN price.
```
curl -XGET 'http://localhost:8080/api/price/btc?date=27-08-2022'
```

### Building the project:

#####Pre-req : gradle , Java

Build the Project  : ./gradlew build

#####Pre-req : Docker , Set the environment varibles in docker-compose.yml


Docker image :  'docker build -f docker/Dockerfile . -t vsn/crypto-tracker'

Run Docker image : under path /docker run - 'docker-compose up'