package com.surely.challenge.adapter.service.cart.dto;

import com.surely.challenge.adapter.service.cartitem.dto.CartItemDTO;
import java.math.BigDecimal;
import java.util.List;

public class CartStateResponseDTO {

    private Long cartId;
    private String status;
    private BigDecimal total;
    private List<CartItemDTO> items;
    private String message;

    public CartStateResponseDTO() {}

    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
