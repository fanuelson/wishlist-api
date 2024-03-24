package com.example.wishlist.gateways.db.documents;

import com.example.wishlist.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDocument {

    private String id;
    private String name;
    private Double price;

    public static ProductDocument create(Product productDomain) {
        ProductDocument product = new ProductDocument();
        product.setId(productDomain.getId());
        product.setName(productDomain.getName());
        product.setPrice(productDomain.getPrice());
        return product;
    }
}
