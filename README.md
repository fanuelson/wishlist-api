# wishlist-api

![Java Badge](https://img.shields.io/badge/Java-21-blue)
![Spring Boot Badge](https://img.shields.io/badge/Spring_Boot-3.2.3-darkgreen)
![Mongo Badge](https://img.shields.io/badge/MongoDB-7.0.7-darkgreen)

## Prerequisites

- Java 21
- Maven 3
- Docker compose

## Run
```
$ mvn clean package
$ docker compose up -d
```

Access:
http://localhost:8080/swagger-ui/index.html

**After runnning `maven clean package`, open `target/site/jacoco/index.html` in browser to see Test Coverage Report**

![image](https://i.ibb.co/N9tS8HQ/tests-coverage-report.png)

<hr/>

## Using
- Clean Arquitecture
- Docker
- MongoDB
- Tests (UnitTests, E2E Tests and SpockTests)

<hr/>

## ENDPOINTS

#### POST ```customers/{customerId}/products```


Body:
```
{
    id: "1",
    name: "Product name",
    price: 10.5
}
```

<hr/>

#### GET ```customers/{customerId}/products```

Response:
```
[
    {
        id: "1",
        name: "Product name",
        price: 10.5
    },
    {
        id: "2",
        name: "Product name 2",
        price: 15.2
    }
]
```
<hr/>


### GET ```/customers/{customerId}/products/{productId}/exists```


Response:
```
{
    exists: true|false
}
```

<hr/>

### DELETE ```/customers/{customerId}/products/{productId}```
