package com.surely.challenge.adapter.service.cart.controller;

import com.surely.challenge.cart.input.DeleteCartInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class DeleteCartController {

    private final DeleteCartInput deleteCartInput;

    public DeleteCartController(DeleteCartInput deleteCartInput) {
        this.deleteCartInput = deleteCartInput;
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {

        deleteCartInput.deleteCart(cartId);

        return ResponseEntity.noContent().build();
    }


}
