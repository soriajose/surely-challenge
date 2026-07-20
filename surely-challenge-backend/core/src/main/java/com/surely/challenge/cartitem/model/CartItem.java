package com.surely.challenge.cartitem.model;

import com.surely.challenge.cartitem.exception.CartItemException;
import com.surely.challenge.product.model.Product;
import java.time.LocalDate;

public class CartItem {
    private Long id;
    private Product product;
    private Integer quantity;
    private String createdBy;
    private LocalDate createdAt;
    private String updatedBy;
    private LocalDate updatedAt;
    private String deletedBy;
    private LocalDate deletedAt;

    private CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static CartItem factoryCartItem(Product product, Integer quantity) {
        if (product == null) {
            throw new CartItemException("El producto no puede ser null");
        }

        if (quantity == null) {
            throw new CartItemException("La cantidad no puede ser null");
        }

        return new CartItem(product, quantity);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
