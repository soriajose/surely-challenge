package com.surely.challenge.adapter.service.cart.controller;

import com.surely.challenge.adapter.service.cart.dto.AddProductToCartRequestDTO;
import com.surely.challenge.adapter.service.cart.dto.CartStateResponseDTO;
import com.surely.challenge.adapter.service.cart.mapper.CartDTOMapper;
import com.surely.challenge.cart.input.AddProductToCartInput;
import com.surely.challenge.cart.model.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class AddProductToCartController {

    private final AddProductToCartInput addProductToCartInput;

    public AddProductToCartController(AddProductToCartInput addProductToCartInput) {
        this.addProductToCartInput = addProductToCartInput;
    }

    @PostMapping("/{cartId}/product")
    public ResponseEntity<?> addProductToCart(@PathVariable Long cartId, @RequestBody AddProductToCartRequestDTO request) {

        Cart cart = addProductToCartInput.addProduct(request.getProductId(), cartId);
        CartStateResponseDTO response = CartDTOMapper.coreToCartStateResponseDtoMapper(cart, "Producto agregado exitosamente al carrito");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
