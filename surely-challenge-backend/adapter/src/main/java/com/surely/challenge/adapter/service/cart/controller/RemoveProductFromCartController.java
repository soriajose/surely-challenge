package com.surely.challenge.adapter.service.cart.controller;

import com.surely.challenge.adapter.service.cart.dto.CartStateResponseDTO;
import com.surely.challenge.adapter.service.cart.mapper.CartDTOMapper;
import com.surely.challenge.cart.input.RemoveProductFromCartInput;
import com.surely.challenge.cart.model.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class RemoveProductFromCartController {

    private final RemoveProductFromCartInput removeProductFromCartInput;

    public RemoveProductFromCartController(RemoveProductFromCartInput removeProductFromCartInput) {
        this.removeProductFromCartInput = removeProductFromCartInput;
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {

        Cart cart = removeProductFromCartInput.removeProduct(productId, cartId);
        CartStateResponseDTO response = CartDTOMapper.coreToCartStateResponseDtoMapper(cart, "Producto eliminado exitosamente del carrito");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
