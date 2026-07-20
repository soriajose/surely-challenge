package usecasetest;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.DeleteCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetCartGateway;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
import com.surely.challenge.cart.usecase.DeleteCartUseCase;
import com.surely.challenge.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCartUseCaseTest {

    DeleteCartInput deleteCartInput;

    @Mock
    GetCartGateway getCartGateway;

    @Mock
    CreateCartGateway createCartGateway;

    @BeforeEach
    void setup() {
        deleteCartInput = new DeleteCartUseCase(getCartGateway, createCartGateway);
    }

    @Test
    void deleteCart_cartExists_softDeletesCartSuccess() {
        User user = User.factoryUser("Juan", "Pérez", "123456", "juan@mail.com", false);
        Cart cart = Cart.factory(user, new NormalPricingStrategy());
        cart.setId(1L);

        when(getCartGateway.findById(1L)).thenReturn(cart);

        deleteCartInput.deleteCart(1L);

        Assertions.assertNotNull(cart.getDeletedAt());
        Assertions.assertEquals(LocalDate.now(), cart.getDeletedAt());
        Assertions.assertEquals("juan@mail.com", cart.getDeletedBy());
        
        verify(createCartGateway, times(1)).saveCart(cart);
    }

    @Test
    void deleteCart_cartNotExists_throwsCartException() {
        when(getCartGateway.findById(99L)).thenReturn(null);

        Exception exception = assertThrows(CartException.class, () -> deleteCartInput.deleteCart(99L));

        Assertions.assertEquals("No existe un cart con el ID: 99", exception.getMessage());
    }
}
