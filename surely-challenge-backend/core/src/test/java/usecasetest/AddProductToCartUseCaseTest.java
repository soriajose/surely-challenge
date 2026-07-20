package usecasetest;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.AddProductToCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetCartGateway;
import com.surely.challenge.cart.strategy.PricingStrategy;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
import com.surely.challenge.cart.usecase.AddProductToCartUseCase;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.product.output.GetProductGateway;
import com.surely.challenge.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddProductToCartUseCaseTest {

    AddProductToCartInput addProductToCartInput;

    @Mock
    CreateCartGateway createCartGateway;
    @Mock
    GetCartGateway getCartGateway;
    @Mock
    GetProductGateway getProductGateway;

    @BeforeEach
    void setup() {
        addProductToCartInput = new AddProductToCartUseCase(createCartGateway, getProductGateway, getCartGateway);
    }

    @Test
    void addProduct_productExist_addProductToCartSuccess() {

        User user = User.factoryUser("Juan", "Pérez", "123456", "juan@mail.com", false);

        PricingStrategy estrategia = new NormalPricingStrategy();

        Cart cart = Cart.factory(user, estrategia);
        cart.setId(123L);

        when(getCartGateway.findById(123L)).thenReturn(cart);

        Product product = Product.factoryProduct("Cafe", "Colombiano", BigDecimal.valueOf(5000));
        product.setId(5L);

        when(getProductGateway.findById(5L)).thenReturn(product);
        when(createCartGateway.saveCart(any(Cart.class))).thenReturn(cart);

        Cart cartResult = addProductToCartInput.addProduct(product.getId(), cart.getId());

        Assertions.assertEquals(cart.getId(), cartResult.getId());


    }

    @Test
    void addProduct_cartNotExist_throwsCartException() {
        // Arrange
        Long cartId = 999L;
        Long productId = 5L;

        when(getCartGateway.findById(cartId)).thenReturn(null);

        Exception exception = assertThrows(CartException.class, () -> addProductToCartInput.addProduct(productId, cartId));

        Assertions.assertEquals("No existe un cart con el ID: " + cartId, exception.getMessage());
    }


}
