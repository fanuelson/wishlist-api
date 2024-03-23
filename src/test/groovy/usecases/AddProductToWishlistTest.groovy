package usecases

import com.example.wishlist.domain.Product
import com.example.wishlist.domain.Wishlist
import com.example.wishlist.gateways.db.WishlistMongoGateway
import com.example.wishlist.gateways.db.WishlistMongoGatewayImpl
import com.example.wishlist.gateways.db.documents.ProductDocument
import com.example.wishlist.gateways.db.documents.WishlistDocument
import com.example.wishlist.gateways.db.repositories.WishlistRepository
import com.example.wishlist.usecases.AddProductToWishlist;
import spock.lang.Specification;

public class AddProductToWishlistTest extends Specification {

    private AddProductToWishlist addProductToWishlist;

    private WishlistRepository wishlistRepository;

    public void setup() {
        wishlistRepository = Stub(WishlistRepository.class);
        WishlistMongoGateway wishlistMongoGateway = new WishlistMongoGatewayImpl(wishlistRepository);
        addProductToWishlist = new AddProductToWishlist(wishlistMongoGateway);
    }
    public void "should create a wishlist"() {
        given: "a customerId"
        String customerId = "1";

        and: "a product"
        Product product = Product.builder()
                .id("1")
                .name("Any name")
                .price(10.5)
                .build();

        and: "empty database"
        wishlistRepository.findByCustomerId(_ as String) >> Optional<WishlistDocument>.empty();

        and: "repository.save"
        WishlistDocument wishlistDocument = WishlistDocument.builder()
                .id("1")
                .customerId(customerId)
                .products(List.of(ProductDocument.create(product)))
                .build();

        wishlistRepository.save(_ as WishlistDocument) >> wishlistDocument

        when: "we execute addProductToWishlist"
        Wishlist wishlist = addProductToWishlist.execute(customerId, product);

        then: "should return wishlist created"
        wishlist.getId() == "1"
        wishlist.getCustomerId() == "1"
        wishlist.getProducts().size() == 1
        wishlist.getProducts().getFirst().getId() == "1"
        wishlist.getProducts().getFirst().getName() == "Any name"
        wishlist.getProducts().getFirst().getPrice() == 10.5

    }
}
