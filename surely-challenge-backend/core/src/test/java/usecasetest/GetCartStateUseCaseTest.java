package usecasetest;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.GetCartStateInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.GetCartGateway;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
import com.surely.challenge.cart.usecase.GetCartStateUseCase;
import com.surely.challenge.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCartStateUseCaseTest {

    GetCartStateInput getCartStateInput;

    @Mock
    GetCartGateway getCartGateway;

    @BeforeEach
    void setup() {
        getCartStateInput = new GetCartStateUseCase(getCartGateway);
    }

    @Test
    void getCartState_cartExists_returnsCartSuccess() {
        User user = User.factoryUser("Juan", "Pérez", "123456", "juan@mail.com", false);
        Cart cart = Cart.factory(user, new NormalPricingStrategy());
        cart.setId(1L);

        when(getCartGateway.findById(1L)).thenReturn(cart);

        Cart result = getCartStateInput.getCartState(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    void getCartState_cartNotExists_throwsCartException() {
        when(getCartGateway.findById(99L)).thenReturn(null);

        Exception exception = assertThrows(CartException.class, () -> {
            getCartStateInput.getCartState(99L);
        });

        Assertions.assertEquals("No existe un cart con el ID: 99", exception.getMessage());
    }

    @Test
    void getCartState_cartIsDeleted_throwsCartException() {
        User user = User.factoryUser("Juan", "Pérez", "123456", "juan@mail.com", false);
        Cart cart = Cart.factory(user, new NormalPricingStrategy());
        cart.setId(1L);
        cart.setDeletedAt(LocalDate.now());

        when(getCartGateway.findById(1L)).thenReturn(cart);

        Exception exception = assertThrows(CartException.class, () -> {
            getCartStateInput.getCartState(1L);
        });

        Assertions.assertEquals("El cart con ID: 1 se encuentra eliminado", exception.getMessage());
    }
}
