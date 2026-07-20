package modeltest;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.strategy.PricingStrategy;
import com.surely.challenge.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CartUnitTest {


    @Test
    void createNew_validArguments_returnEmptyCart() {

        User user = User.factoryUser("Juan", "Perez", "40123456", "juan@mail.com", false);
        PricingStrategy mockStrategy = Mockito.mock(PricingStrategy.class);
        Cart cart = Cart.factory(user, mockStrategy);
        Assertions.assertNotNull(cart);
    }

    @Test
    void createNew_nullUser_throwCartException() {

        PricingStrategy mockStrategy = Mockito.mock(PricingStrategy.class);
        Assertions.assertThrows(CartException.class, () -> Cart.factory(null, mockStrategy));
    }


}
