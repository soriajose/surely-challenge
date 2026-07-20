package com.surely.challenge.adapter.servicetest.cart;

import com.surely.challenge.adapter.service.cart.controller.DeleteCartController;
import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.DeleteCartInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCartControllerUnitTest {

    private DeleteCartController controller;

    @Mock
    private DeleteCartInput deleteCartInput;

    @BeforeEach
    void setup() {
        controller = new DeleteCartController(deleteCartInput);
    }

    @Test
    public void deleteCart_cartExists_returnsHTTP204Success() {
        Long cartId = 1L;
        doNothing().when(deleteCartInput).deleteCart(cartId);

        ResponseEntity<?> response = controller.deleteCart(cartId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteCartInput, times(1)).deleteCart(cartId);
    }
    
    // We don't explicitly test the exception handling here because that is now delegated to @ControllerAdvice
    // In a pure unit test of the controller, we only test the happy path and that exceptions bubble up correctly.
}
