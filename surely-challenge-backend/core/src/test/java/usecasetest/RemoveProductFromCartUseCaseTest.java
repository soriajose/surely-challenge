package usecasetest;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.RemoveProductFromCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetCartGateway;
import com.surely.challenge.cart.output.RemoveProductFromCartGateway;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
import com.surely.challenge.cart.usecase.RemoveProductFromCartUseCase;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveProductFromCartUseCaseTest {

    RemoveProductFromCartInput removeProductFromCartInput;

    @Mock
    GetCartGateway getCartGateway;

    @Mock
    RemoveProductFromCartGateway removeProductFromCartGateway;

    @BeforeEach
    void setup() {
        removeProductFromCartInput = new RemoveProductFromCartUseCase(getCartGateway, removeProductFromCartGateway);
    }

    @Test
    void removeProduct_productExists_softDeletesItemSuccess() {
        User user = User.factoryUser("Juan", "Pérez", "123456", "juan@mail.com", false);
        Cart cart = Cart.factory(user, new NormalPricingStrategy());
        cart.setId(1L);

        Product product = Product.factoryProduct("Cafe", "Desc", BigDecimal.valueOf(100));
        product.setId(5L);
        cart.addProduct(product);

        when(getCartGateway.findById(1L)).thenReturn(cart);
        when(removeProductFromCartGateway.removeProduct(any(Cart.class))).thenReturn(cart);

        Cart cartResult = removeProductFromCartInput.removeProduct(5L, 1L);

        Assertions.assertNotNull(cartResult.getItems().get(0).getDeletedAt());
        Assertions.assertEquals(LocalDate.now(), cartResult.getItems().get(0).getDeletedAt());
        Assertions.assertEquals("juan@mail.com", cartResult.getItems().get(0).getDeletedBy());
        verify(removeProductFromCartGateway).removeProduct(cart);
    }

    @Test
    void removeProduct_cartNotExists_throwsCartException() {
        when(getCartGateway.findById(99L)).thenReturn(null);

        Exception exception = assertThrows(CartException.class, () -> removeProductFromCartInput.removeProduct(5L, 99L));

        Assertions.assertEquals("No existe un cart con el ID: 99", exception.getMessage());
    }

    @Test
    void removeProduct_productNotInCart_throwsCartException() {
        User user = User.factoryUser("Juan", "Pérez", "123456", "juan@mail.com", false);
        Cart cart = Cart.factory(user, new NormalPricingStrategy());
        cart.setId(1L);

        when(getCartGateway.findById(1L)).thenReturn(cart);

        Exception exception = assertThrows(CartException.class, () -> removeProductFromCartInput.removeProduct(5L, 1L));

        Assertions.assertEquals("El producto no se encuentra en el carrito", exception.getMessage());
    }
}
