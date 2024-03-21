package com.example.wishlist.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class WishlistId {
    private String customerId;
    private String productId;
}
