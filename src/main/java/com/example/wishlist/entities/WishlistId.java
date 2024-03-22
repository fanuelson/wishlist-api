package com.example.wishlist.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
public class WishlistId {
    private String customerId;
    private String productId;

    private WishlistId() {
    }

    public static WishlistId create(String customerId, String productId) {
        WishlistId id = new WishlistId();
        id.setCustomerId(customerId);
        id.setProductId(productId);
        return id;
    }

}
