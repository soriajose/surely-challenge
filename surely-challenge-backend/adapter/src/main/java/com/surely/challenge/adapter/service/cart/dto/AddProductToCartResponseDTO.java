package com.surely.challenge.adapter.service.cart.dto;

import com.surely.challenge.adapter.service.cartitem.dto.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddProductToCartResponseDTO {
    private Long cartId;
    private String message;
    private BigDecimal totalAmount;
    private List<CartItemDTO> items;
}
