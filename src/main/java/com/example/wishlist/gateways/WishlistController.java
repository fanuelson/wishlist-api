package com.example.wishlist.gateways;

import com.example.wishlist.entities.Wishlist;
import com.example.wishlist.entities.WishlistId;
import com.example.wishlist.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistRepository wishlistRepository;
    @PostMapping("customers/{customerId}/products/{productId}")
    public void addProduct(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId) {
        WishlistId id = new WishlistId();
        id.setCustomerId(customerId);
        id.setProductId(productId);
        Wishlist wishlist = new Wishlist();
        wishlist.setId(id);
        this.wishlistRepository.save(wishlist);
    }

    @DeleteMapping("/customers/{customerId}/products/{productId}")
    public void removeProduct(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId) {
        WishlistId id = new WishlistId();
        id.setCustomerId(customerId);
        id.setProductId(productId);
        Wishlist wishlist = new Wishlist();
        wishlist.setId(id);
        this.wishlistRepository.deleteById(id);
    }
    @GetMapping("customers/{customerId}/products")
    public List<Wishlist> findAllProductsByCustomer(@PathVariable(value = "customerId") String customerId) {
        return this.wishlistRepository.findByIdCustomerId(customerId);
    }

    @GetMapping("/customers/{customerId}/products/{productId}/exists")
    public boolean existsProduct(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId) {
        WishlistId id = new WishlistId();
        id.setCustomerId(customerId);
        id.setProductId(productId);
        Wishlist wishlist = new Wishlist();
        wishlist.setId(id);
        return this.wishlistRepository.existsById(id);
    }

}
