package com.surely.challenge.adapter.service.cart.controller;

import com.surely.challenge.adapter.service.cart.dto.CreateCartRequestDTO;
import com.surely.challenge.adapter.service.cart.dto.CreateCartResponseDTO;
import com.surely.challenge.cart.input.CreateCartInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CreateCartController {

    private final CreateCartInput createCartInput;

    public CreateCartController(CreateCartInput createCartInput) {
        this.createCartInput = createCartInput;
    }

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody CreateCartRequestDTO request) {

        Long cartId = createCartInput.createCart(request.getDocumentNumber());
        CreateCartResponseDTO createCartResponseDTO = new CreateCartResponseDTO();
        createCartResponseDTO.setCartId(cartId);
        createCartResponseDTO.setMessage("Carrito creado exitosamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(createCartResponseDTO);
    }
}
