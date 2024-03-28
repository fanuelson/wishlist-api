package usecases

import com.example.wishlist.domain.Product
import com.example.wishlist.exceptions.WishlistNotFoundException
import com.example.wishlist.gateways.db.WishlistDbGateway
import com.example.wishlist.gateways.db.WishlistMongoGatewayImpl
import com.example.wishlist.mocks.ProductMock
import com.example.wishlist.mocks.WishlistMock
import com.example.wishlist.usecases.ExistsProductInWishlist
import spock.lang.Specification

class ExistsProductInWishlistSpockTest extends Specification {

    private ExistsProductInWishlist existsProductInWishlist

    private WishlistDbGateway wishlistDbGateway

    void setup() {
        wishlistDbGateway = Mock(WishlistMongoGatewayImpl.class);
        existsProductInWishlist = new ExistsProductInWishlist(wishlistDbGateway);
    }

    void "should throw NOT FOUND WHEN checking for a product in a non existing wishlist "() {
        given: "a customerId"
        def customerId = "1"

        and: "a productId"
        def productId = "1";

        and: "empty database"
        wishlistDbGateway.findByCustomerId(_ as String) >> Optional.empty()

        when: "we execute exists"
        existsProductInWishlist.execute(customerId, productId)

        then:
        thrown(WishlistNotFoundException)
    }

    void "should return exists WHEN checking if product exists"() {
        given: "a customerId"
        def customerId = "1"

        and: "a productId"
        def productId = "1";

        and: "database"
        wishlistDbGateway.findByCustomerId(_ as String) >> Optional.of(wishlist);

        when: "we execute exists"
        def exists = existsProductInWishlist.execute(customerId, productId)

        then:
        exists == expectedExists;

        where:
        wishlist                                                                                |expectedExists
        WishlistMock.create()                                                                   |false
        WishlistMock.create(new ArrayList<Product>(Arrays.asList(ProductMock.create("2"))))     |false
        WishlistMock.create(new ArrayList<Product>(Arrays.asList(ProductMock.create())))        |true
    }
}
