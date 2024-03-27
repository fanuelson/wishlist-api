package usecases

import com.example.wishlist.domain.Product
import com.example.wishlist.gateways.db.WishlistDbGateway
import com.example.wishlist.gateways.db.WishlistMongoGatewayImpl
import com.example.wishlist.gateways.db.documents.ProductDocument
import com.example.wishlist.gateways.db.documents.WishlistDocument
import com.example.wishlist.gateways.db.repositories.WishlistMongoRepository
import com.example.wishlist.mocks.ProductMock
import com.example.wishlist.mocks.WishlistDocumentMock
import com.example.wishlist.usecases.AddProductToWishlist
import com.example.wishlist.usecases.validators.MaxProductsValidator
import com.example.wishlist.usecases.validators.ValidationProperties
import spock.lang.Specification

class AddProductToWishlistTest extends Specification {

    private AddProductToWishlist addProductToWishlist

    private WishlistMongoRepository wishlistRepository

    void setup() {
        wishlistRepository = Stub(WishlistMongoRepository.class)
        WishlistDbGateway wishlistMongoGateway = new WishlistMongoGatewayImpl(wishlistRepository)
        ValidationProperties validationProperties = new ValidationProperties();
        validationProperties.setMaxProducts(20);
        MaxProductsValidator maxProductsValidator = new MaxProductsValidator(validationProperties);
        addProductToWishlist = new AddProductToWishlist(wishlistMongoGateway, maxProductsValidator)
    }

    void "should create a wishlist"() {
        given: "a customerId"
        def customerId = "1"

        and: "a product"
        def product = ProductMock.create("1");

        and: "empty database"
        wishlistRepository.findByCustomerId(_ as String) >> Optional.empty()

        and: "repository.save"
        def wishlistDocument = WishlistDocumentMock.create(customerId, List.of(ProductDocument.create(product)));

        wishlistRepository.save(_ as WishlistDocument) >> wishlistDocument

        when: "we execute addProductToWishlist"
        def wishlist = addProductToWishlist.execute(customerId, product)

        then: "should return wishlist created"
        verifyAll(wishlist) {
            customerId == "1"
            products.size() == 1
            products.first.id == "1"
            products.first.name == "Product1"
            products.first.price == 10.5
        }
    }
}
