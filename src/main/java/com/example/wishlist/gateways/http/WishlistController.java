package com.example.wishlist.gateways.http;

import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;
import com.example.wishlist.gateways.http.dtos.response.ProductExistsResponseDTO;
import com.example.wishlist.gateways.http.dtos.response.ProductResponseDTO;
import com.example.wishlist.usecases.AddProductToWishlist;
import com.example.wishlist.usecases.ExistsProductInWishlist;
import com.example.wishlist.usecases.FindAllProductsByCustomer;
import com.example.wishlist.usecases.RemoveProductInWishlist;
import com.example.wishlist.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final AddProductToWishlist addProductToWishlist;

    private final ExistsProductInWishlist existsProductInWishlist;

    private final FindAllProductsByCustomer findAllProductsByCustomer;

    private final RemoveProductInWishlist removeProductInWishlist;

    @PostMapping("customers/{customerId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@PathVariable(value = "customerId") final String customerId, @RequestBody final ProductRequestDTO productDTO) {
        addProductToWishlist.execute(customerId, Product.create(productDTO));
    }

    @DeleteMapping("/customers/{customerId}/products/{productId}")
    public void removeProduct(@PathVariable(value = "customerId") final String customerId, @PathVariable(value = "productId") final String productId) {
        this.removeProductInWishlist.execute(customerId, productId);
    }
    @GetMapping("customers/{customerId}/products")
    public List<ProductResponseDTO> findAllProductsByCustomer(@PathVariable(value = "customerId") final String customerId) {
        List<Product> products = this.findAllProductsByCustomer.execute(customerId);
        return products.stream().map(ProductResponseDTO::create).toList();
    }

    @GetMapping("/customers/{customerId}/products/{productId}/exists")
    public ProductExistsResponseDTO existsProduct(@PathVariable(value = "customerId") final String customerId, @PathVariable(value = "productId") final String productId) {
        boolean exists = this.existsProductInWishlist.execute(customerId, productId);
        return ProductExistsResponseDTO.create(exists);
    }

}
