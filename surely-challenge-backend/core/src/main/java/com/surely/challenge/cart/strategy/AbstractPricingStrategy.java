package com.surely.challenge.cart.strategy;

import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cartitem.model.CartItem;

import java.math.BigDecimal;

public abstract class AbstractPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculateTotal(Cart cart) {

        BigDecimal descuento = new BigDecimal("0.10");

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal baseTotal = calculateBaseTotal(cart);

        int totalItems = cart.getItems().stream()
                .filter(item -> item.getDeletedAt() == null)
                .mapToInt(CartItem::getQuantity).sum();

        if (totalItems == 10) {
            BigDecimal descuentoTotal = baseTotal.multiply(descuento);
            baseTotal = baseTotal.subtract(descuentoTotal);
        }

        return applyDiscount(cart, baseTotal, totalItems);
    }


    public BigDecimal calculateBaseTotal(Cart cart) {

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            if (item.getDeletedAt() == null) {
                BigDecimal subtotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(subtotal);
            }
        }

        return total;
    }

    public abstract BigDecimal applyDiscount(Cart cart, BigDecimal total, int totalItems);

}
