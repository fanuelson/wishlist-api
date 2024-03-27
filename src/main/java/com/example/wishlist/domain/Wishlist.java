package com.example.wishlist.domain;

import com.example.wishlist.gateways.db.documents.WishlistDocument;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
public class Wishlist {

    private String id;
    private String customerId;
    private List<Product> products;

    public static Wishlist create(final WishlistDocument wishlistDocument) {
        return Wishlist
                .builder()
                .id(wishlistDocument.getId())
                .customerId(wishlistDocument.getCustomerId())
                .products(wishlistDocument.getProducts().stream().map(Product::create).collect(Collectors.toList()))
                .build();
    }

    public static Wishlist create(final String customerId) {
        return Wishlist
                .builder()
                .customerId(customerId)
                .products(new ArrayList<>())
                .build();
    }

    public Wishlist withProductRemoved(final Product product) {
        products = this.getProducts().stream().filter(p -> p.equals(product)).toList();
        return this;
    }

    public Optional<Product> findProductById(final String productId) {
        final int indexOf = this.getProducts().indexOf(Product.create(productId));
        if (indexOf >= 0) {
            return Optional.of(this.getProducts().get(indexOf));
        }
        return Optional.empty();
    }

    public void addOrUpdateProduct(final Product product) {
        final int indexOfProduct = this.getProducts().indexOf(product);
        if (indexOfProduct >= 0) {
            this.getProducts().set(indexOfProduct, product);
        } else {
            this.getProducts().addFirst(product);
        }
    }
}
