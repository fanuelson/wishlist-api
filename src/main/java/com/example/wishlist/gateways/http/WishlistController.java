package com.example.wishlist.gateways.http;

import com.example.wishlist.domain.Product;
import com.example.wishlist.gateways.http.dtos.request.ProductRequestDTO;
import com.example.wishlist.gateways.http.dtos.response.ProductExistsResponseDTO;
import com.example.wishlist.gateways.http.dtos.response.ProductResponseDTO;
import com.example.wishlist.usecases.AddProductToWishlist;
import com.example.wishlist.usecases.ExistsProductInWishlist;
import com.example.wishlist.usecases.FindAllProductsByCustomer;
import com.example.wishlist.usecases.RemoveProductInWishlist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/customers/{customerId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add a product to customer's wishlist",
            responses = {
                    @ApiResponse(responseCode = "201", description = "The product has been added to the customer's wishlist"),
                    @ApiResponse(responseCode = "400", description = "Wishlist of this customer has already reached the limit of 20 products", content = {
                            @Content(schema = @Schema(oneOf = ResponseEntity.class))
                    })
            }
    )
    public void addProduct(@PathVariable(value = "customerId") final String customerId,
                           @RequestBody final ProductRequestDTO productDTO) {
        addProductToWishlist.execute(customerId, Product.create(productDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/customers/{customerId}/products/{productId}")
    public ProductResponseDTO removeProduct(@PathVariable(value = "customerId") final String customerId,
                                            @PathVariable(value = "productId") final String productId) {
        Product productRemoved = this.removeProductInWishlist.execute(customerId, productId);
        return ProductResponseDTO.create(productRemoved);

    }

    @GetMapping("/customers/{customerId}/products")
    public List<ProductResponseDTO> findAllProductsByCustomer(@PathVariable(value = "customerId") final String customerId) {
        List<Product> products = this.findAllProductsByCustomer.execute(customerId);
        return products.stream().map(ProductResponseDTO::create).toList();
    }

    @GetMapping("/customers/{customerId}/products/{productId}/exists")
    public ProductExistsResponseDTO existsProduct(@PathVariable(value = "customerId") final String customerId,
                                                  @PathVariable(value = "productId") final String productId) {
        boolean exists = this.existsProductInWishlist.execute(customerId, productId);
        return ProductExistsResponseDTO.create(exists);
    }

}
