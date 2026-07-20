package com.surely.challenge.cart.model;

import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.strategy.PricingStrategy;
import com.surely.challenge.cartitem.model.CartItem;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.user.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Cart {
    private Long id;
    private User user;
    private List<CartItem> items;
    private CartStatus status;
    private PricingStrategy pricingStrategy;

    private String createdBy;
    private LocalDate createdAt;
    private String updatedBy;
    private LocalDate updatedAt;
    private String deletedBy;
    private LocalDate deletedAt;

    private Cart(Long id, User user, List<CartItem> items, CartStatus status, PricingStrategy pricingStrategy) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.status = status;
        this.pricingStrategy = pricingStrategy;
    }

    public static Cart factory(User user, PricingStrategy pricingStrategy) {
        if (user == null) {
            throw new CartException("El usuario es obligatorio.");
        }
        if (pricingStrategy == null) {
            throw new CartException("La estrategia es obligatoria.");
        }
        return new Cart(null, user, new ArrayList<>(), CartStatus.OPEN, pricingStrategy);
    }

    public BigDecimal calculateTotal() {
        return this.pricingStrategy.calculateTotal(this);
    }

    public void addProduct(Product product) {

        for (CartItem item : this.items) {
            if (item.getProduct().getId().equals(product.getId()) && item.getDeletedAt() == null) {
                item.setQuantity(item.getQuantity() + 1);
                item.setUpdatedAt(LocalDate.now());
                item.setUpdatedBy(this.user.getEmail());
                return;
            }
        }

        CartItem newItem = CartItem.factoryCartItem(product, 1);
        newItem.setCreatedAt(LocalDate.now());
        newItem.setCreatedBy(this.user.getEmail());
        this.items.add(newItem);
    }

    public void removeProduct(Long productId) {
        CartItem itemRemove = null;

        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId) && item.getDeletedAt() == null) {
                itemRemove = item;
                break;
            }
        }

        if (itemRemove == null) {
            throw new CartException("El producto no se encuentra en el carrito");
        }

        if (itemRemove.getQuantity() > 1) {
            itemRemove.setQuantity(itemRemove.getQuantity() - 1);
            itemRemove.setUpdatedAt(LocalDate.now());
            itemRemove.setUpdatedBy(this.user.getEmail());
        } else {
            itemRemove.setDeletedAt(LocalDate.now());
            itemRemove.setDeletedBy(this.user.getEmail());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }
}
