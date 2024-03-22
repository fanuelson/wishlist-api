package com.example.wishlist.gateways.http;

import com.example.wishlist.dtos.request.ProductDTO;
import com.example.wishlist.dtos.response.ExistsProductResponseDTO;
import com.example.wishlist.dtos.response.FindAllProductsResponseDTO;
import com.example.wishlist.entities.Product;
import com.example.wishlist.entities.Wishlist;
import com.example.wishlist.entities.WishlistId;
import com.example.wishlist.gateways.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistRepository wishlistRepository;
    @PostMapping("customers/{customerId}/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@PathVariable(value = "customerId") final String customerId, @PathVariable(value = "productId") final String productId, @RequestBody final ProductDTO productDTO) {
        Wishlist wishlist = Wishlist.create(WishlistId.create(customerId, productId), Product.create(productDTO));
        this.wishlistRepository.insert(wishlist);
    }

    @DeleteMapping("/customers/{customerId}/products/{productId}")
    public void removeProduct(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId) {
        WishlistId id = WishlistId.create(customerId, productId);
        this.wishlistRepository.deleteById(id);
    }
    @GetMapping("customers/{customerId}/products")
    public List<FindAllProductsResponseDTO> findAllProductsByCustomer(@PathVariable(value = "customerId") String customerId) {
        List<Wishlist> wishlist = this.wishlistRepository.findByIdCustomerId(customerId);
        return wishlist.stream().map((_wishlist) -> FindAllProductsResponseDTO.create(_wishlist.getProduct())).toList();
    }

    @GetMapping("/customers/{customerId}/products/{productId}/exists")
    public ExistsProductResponseDTO existsProduct(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId) {
        WishlistId id = WishlistId.create(customerId, productId);
        Optional<Wishlist> wishlist = this.wishlistRepository.findById(id);
        return ExistsProductResponseDTO.create(wishlist.isPresent());
    }

}
