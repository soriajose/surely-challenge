package com.surely.challenge.adapter.service.cart.controller;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.CheckoutCartInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CheckoutCartController {

    private final CheckoutCartInput checkoutCartInput;

    public CheckoutCartController(CheckoutCartInput checkoutCartInput) {
        this.checkoutCartInput = checkoutCartInput;
    }

    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<?> checkoutCart(@PathVariable Long cartId) {
        try {
            checkoutCartInput.checkoutCart(cartId);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Compra finalizada con éxito"));
        } catch (CartException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Ocurrió un error inesperado al finalizar la compra"));
        }
    }
}
