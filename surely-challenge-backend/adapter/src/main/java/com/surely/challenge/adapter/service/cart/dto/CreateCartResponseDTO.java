package com.surely.challenge.adapter.service.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateCartResponseDTO {

    private Long cartId;
    private String message;
}
