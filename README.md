# wishlist-api

![Java Badge](https://img.shields.io/badge/Java-21-blue)
![Spring Boot Badge](https://img.shields.io/badge/Spring_Boot-3.2.3-darkgreen)
![Mongo Badge](https://img.shields.io/badge/MongoDB-7.0.7-darkgreen)

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

<h4>GET
```customers/{customerId}/products```
</h4>

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

<h4>GET
```/customers/{customerId}/products/{productId}/exists```
</h4>


Response:
```
{
    exists: true|false
}
```

<hr/>

<h4>DELETE
```/customers/{customerId}/products/{productId}```
</h4>