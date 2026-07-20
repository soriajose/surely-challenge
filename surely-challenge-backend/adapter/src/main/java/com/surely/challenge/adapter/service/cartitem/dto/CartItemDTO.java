package com.surely.challenge.adapter.service.cartitem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal subtotal;
}
