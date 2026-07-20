package com.surely.challenge.adapter.servicetest.cart;

import com.surely.challenge.adapter.service.cart.controller.CreateCartController;
import com.surely.challenge.adapter.service.cart.dto.CreateCartRequestDTO;
import com.surely.challenge.adapter.service.cart.dto.CreateCartResponseDTO;
import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.CreateCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
import com.surely.challenge.user.exception.UserException;
import com.surely.challenge.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCartControllerUnitTest {

    private CreateCartController createCartController;

    @Mock
    private CreateCartInput createCartInput;

    @BeforeEach
    void setup() {
        createCartController = new CreateCartController(createCartInput);
    }

    @Test
    public void createCart_userExists_returnsHTTP201Success() {
        CreateCartRequestDTO request = new CreateCartRequestDTO("12345678");
        User user = User.factoryUser("Juan", "Perez", "12345678", "juan@gmail.com", false);
        Cart cart = Cart.factory(user, new NormalPricingStrategy());
        cart.setId(1L);

        when(createCartInput.createCart(request.getDocumentNumber())).thenReturn(cart.getId());


        ResponseEntity<?> result = createCartController.createCart(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        CreateCartResponseDTO body = (CreateCartResponseDTO) result.getBody();
        assertEquals(1L, body.getCartId());
    }

    @Test
    public void createCart_userNotExists_throwsUserException() {
        // arrange
        CreateCartRequestDTO request = new CreateCartRequestDTO("00000000");
        UserException exception = new UserException("El usuario con documento 00000000 no existe");
        when(createCartInput.createCart(request.getDocumentNumber())).thenThrow(exception);

        // act & assert
        UserException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                UserException.class,
                () -> createCartController.createCart(request)
        );

        org.junit.jupiter.api.Assertions.assertEquals("El usuario con documento 00000000 no existe", thrown.getMessage());
    }
}
