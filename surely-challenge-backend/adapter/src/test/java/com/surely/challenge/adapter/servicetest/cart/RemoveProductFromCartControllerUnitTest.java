package com.surely.challenge.adapter.servicetest.cart;

import com.surely.challenge.adapter.service.cart.controller.RemoveProductFromCartController;
import com.surely.challenge.adapter.service.cart.dto.CartStateResponseDTO;
import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.RemoveProductFromCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveProductFromCartControllerUnitTest {

    private RemoveProductFromCartController removeProductFromCartController;

    @Mock
    private RemoveProductFromCartInput removeProductFromCartInput;

    @BeforeEach
    void setup() {
        removeProductFromCartController = new RemoveProductFromCartController(removeProductFromCartInput);
    }

    @Test
    void removeProductFromCart_productExists_returnsHTTP200Success() {
        Long cartId = 1L;
        Long productId = 5L;

        User user = User.factoryUser("Test", "User", "12345678", "test@mail.com", false);
        Cart cart = Cart.factory(user, new NormalPricingStrategy());
        cart.setId(cartId);

        when(removeProductFromCartInput.removeProduct(productId, cartId)).thenReturn(cart);

        ResponseEntity<?> responseEntity = removeProductFromCartController.removeProductFromCart(cartId, productId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        CartStateResponseDTO response = (CartStateResponseDTO) responseEntity.getBody();
        assertEquals("Producto eliminado exitosamente del carrito", response.getMessage());
        assertEquals(cartId, response.getCartId());
    }

    @Test
    void removeProductFromCart_productNotExists_throwsCartException() {
        Long cartId = 1L;
        Long productId = 5L;

        when(removeProductFromCartInput.removeProduct(productId, cartId))
                .thenThrow(new CartException("El producto no se encuentra en el carrito"));

        CartException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                CartException.class,
                () -> removeProductFromCartController.removeProductFromCart(cartId, productId)
        );

        org.junit.jupiter.api.Assertions.assertEquals("El producto no se encuentra en el carrito", thrown.getMessage());
    }

    @Test
    void removeProductFromCart_unexpectedError_throwsRuntimeException() {
        Long cartId = 1L;
        Long productId = 5L;

        when(removeProductFromCartInput.removeProduct(productId, cartId))
                .thenThrow(new RuntimeException("Error inesperado"));

        RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> removeProductFromCartController.removeProductFromCart(cartId, productId)
        );

        org.junit.jupiter.api.Assertions.assertEquals("Error inesperado", thrown.getMessage());
    }
}
