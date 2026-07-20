package com.surely.challenge.adapter.service.cart.controller;

import com.surely.challenge.adapter.service.cart.dto.CartStateResponseDTO;
import com.surely.challenge.adapter.service.cart.mapper.CartDTOMapper;
import com.surely.challenge.cart.input.GetCartStateInput;
import com.surely.challenge.cart.model.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class GetCartStateController {

    private final GetCartStateInput getCartStateInput;

    public GetCartStateController(GetCartStateInput getCartStateInput) {
        this.getCartStateInput = getCartStateInput;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartStateResponseDTO> getCartState(@PathVariable Long cartId) {

        Cart cart = getCartStateInput.getCartState(cartId);
        CartStateResponseDTO response = CartDTOMapper.coreToCartStateResponseDtoMapper(cart, "Estado del carrito");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
