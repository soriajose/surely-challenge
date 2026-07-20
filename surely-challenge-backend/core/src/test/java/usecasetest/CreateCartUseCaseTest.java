package usecasetest;

import com.surely.challenge.cart.factory.CartFactory;
import com.surely.challenge.cart.input.CreateCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.output.GetPromotionalDateGateway;
import com.surely.challenge.cart.usecase.CreateCartUseCase;
import com.surely.challenge.user.exception.UserException;
import com.surely.challenge.user.model.User;
import com.surely.challenge.user.output.GetUserGateway;
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
public class CreateCartUseCaseTest {

    CreateCartInput createCartInput;

    @Mock
    CreateCartGateway createCartGateway;
    
    @Mock
    GetUserGateway getUserGateway;

    @Mock
    GetPromotionalDateGateway getPromotionalDateGateway;

    @BeforeEach
    void setup() {
        createCartInput = new CreateCartUseCase(createCartGateway, getUserGateway, getPromotionalDateGateway);
    }

    @Test
    public void createCart_userExistAndPromotionalDate_createCartSuccess() {

        //Arange
        User user = User.factoryUser("Juan", "Pérez", "123456", "juan@mail.com", false);

        when(getUserGateway.findByDocument("123456")).thenReturn(user);
        when(getPromotionalDateGateway.isPromotionalDate(any())).thenReturn(true);

        Cart cartMock = CartFactory.createCartFactory(user, true);
        cartMock.setId(1L);

        when(createCartGateway.saveCart(any(Cart.class))).thenReturn(cartMock);

        // Act
        Long result = createCartInput.createCart("123456");

        // Assert
        Assertions.assertEquals(1L, result);
        verify(getPromotionalDateGateway, times(1)).isPromotionalDate(any());
    }
    
    @Test
    public void createCart_userExistAndNotPromotionalDate_createCartSuccess() {

        //Arange
        User user = User.factoryUser("Juan", "Pérez", "123456", "juan@mail.com", false);

        when(getUserGateway.findByDocument("123456")).thenReturn(user);
        when(getPromotionalDateGateway.isPromotionalDate(any())).thenReturn(false);

        Cart cartMock = CartFactory.createCartFactory(user, false);
        cartMock.setId(1L);

        when(createCartGateway.saveCart(any(Cart.class))).thenReturn(cartMock);

        // Act
        Long result = createCartInput.createCart("123456");

        // Assert
        Assertions.assertEquals(1L, result);
        verify(getPromotionalDateGateway, times(1)).isPromotionalDate(any());
    }

    @Test
    public void createCart_userNotExist_throwsUserException() {

        String documento = "123456";

        //Arange
        when(getUserGateway.findByDocument(documento)).thenReturn(null);

        // Act
        Exception exception = assertThrows(UserException.class, () -> createCartInput.createCart(documento));

        // Assert
        Assertions.assertEquals("No existe un usuario registrado para el documento: " + documento, exception.getMessage());
        verify(getPromotionalDateGateway, never()).isPromotionalDate(any());
    }

}
