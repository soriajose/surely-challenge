package com.surely.challenge.adapter.service.cart.mapper;

import com.surely.challenge.adapter.service.cart.dto.CartStateResponseDTO;
import com.surely.challenge.adapter.service.cartitem.mapper.CartItemDTOMapper;
import com.surely.challenge.cart.model.Cart;

import java.util.stream.Collectors;

public class CartDTOMapper {

    private CartDTOMapper() {
    }

    public static CartStateResponseDTO coreToCartStateResponseDtoMapper(Cart cart, String message) {
        CartStateResponseDTO response = new CartStateResponseDTO();
        response.setCartId(cart.getId());
        response.setStatus(cart.getStatus().name());
        response.setTotal(cart.calculateTotal());
        response.setMessage(message);

        if (cart.getItems() != null) {
            response.setItems(cart.getItems().stream()
                    .filter(item -> item.getDeletedAt() == null)
                    .map(CartItemDTOMapper::coreToDtoMapper)
                    .collect(Collectors.toList()));
        }

        return response;
    }
}
