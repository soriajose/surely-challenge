package com.surely.challenge.adapter.service.cartitem.mapper;

import com.surely.challenge.adapter.service.cartitem.dto.CartItemDTO;
import com.surely.challenge.cartitem.model.CartItem;

import java.math.BigDecimal;

public class CartItemDTOMapper {

    private CartItemDTOMapper() {
    }

    public static CartItemDTO coreToDtoMapper(CartItem item) {
        BigDecimal subtotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        return new CartItemDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                subtotal
        );
    }
}
