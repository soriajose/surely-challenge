package com.surely.challenge.adapter.data.cart.mapper;

import com.surely.challenge.adapter.data.cart.model.CartEntity;
import com.surely.challenge.adapter.data.cartitem.model.CartItemEntity;
import com.surely.challenge.adapter.data.product.mapper.ProductDataMapper;
import com.surely.challenge.adapter.data.user.mapper.UserDataMapper;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cartitem.model.CartItem;
import com.surely.challenge.cart.strategy.PricingStrategy;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
import com.surely.challenge.cart.strategy.implementation.SpecialDatePricingStrategy;
import com.surely.challenge.cart.strategy.implementation.VipPricingStrategy;

import java.util.stream.Collectors;

public class CartDataMapper {

    public static Cart dataEntityToCoreMapper(CartEntity cartEntity) {

        if (cartEntity == null) {
            return null;
        }

        PricingStrategy strategy;

        if ("VipPricingStrategy".equals(cartEntity.getStrategyName())) {
            strategy = new VipPricingStrategy();
        } else if ("SpecialDatePricingStrategy".equals(cartEntity.getStrategyName())) {
            strategy = new SpecialDatePricingStrategy();
        } else {
            strategy = new NormalPricingStrategy();
        }

        Cart cart = Cart.factory(UserDataMapper.dataCoreMapper(cartEntity.getUser()), strategy);

        cart.setId(cartEntity.getId());
        cart.setCreatedAt(cartEntity.getCreatedAt());
        cart.setCreatedBy(cartEntity.getCreatedBy());
        cart.setUpdatedAt(cartEntity.getUpdatedAt());
        cart.setUpdatedBy(cartEntity.getUpdatedBy());
        cart.setDeletedAt(cartEntity.getDeletedAt());
        cart.setDeletedBy(cartEntity.getDeletedBy());
        cart.setStatus(cartEntity.getStatus());

        if (cartEntity.getItems() != null) {
            cart.setItems(cartEntity.getItems().stream().map(itemEntity -> {
                CartItem item = CartItem.factoryCartItem(
                        ProductDataMapper.dataEntityToCoreMapper(itemEntity.getProduct()),
                        itemEntity.getQuantity()
                );
                item.setId(itemEntity.getId());
                item.setCreatedAt(itemEntity.getCreatedAt());
                item.setCreatedBy(itemEntity.getCreatedBy());
                item.setUpdatedAt(itemEntity.getUpdatedAt());
                item.setUpdatedBy(itemEntity.getUpdatedBy());
                item.setDeletedAt(itemEntity.getDeletedAt());
                item.setDeletedBy(itemEntity.getDeletedBy());
                return item;
            }).collect(Collectors.toList()));
        }

        return cart;
    }

    public static CartEntity dataCoreToEntityMapper(Cart cart) {
        if (cart == null) return null;

        CartEntity entity = new CartEntity();
        entity.setId(cart.getId());
        entity.setUser(UserDataMapper.dataEntityMapper(cart.getUser()));
        entity.setStrategyName(cart.getPricingStrategy().getClass().getSimpleName());
        entity.setCreatedAt(cart.getCreatedAt());
        entity.setCreatedBy(cart.getCreatedBy());
        entity.setUpdatedAt(cart.getUpdatedAt());
        entity.setUpdatedBy(cart.getUpdatedBy());
        entity.setDeletedAt(cart.getDeletedAt());
        entity.setDeletedBy(cart.getDeletedBy());
        entity.setStatus(cart.getStatus());

        if (cart.getItems() != null) {
            entity.setItems(cart.getItems().stream().map(item -> {
                CartItemEntity itemEntity = new CartItemEntity();
                itemEntity.setId(item.getId());
                itemEntity.setCart(entity);
                itemEntity.setProduct(ProductDataMapper.dataCoreToEntityMapper(item.getProduct()));
                itemEntity.setQuantity(item.getQuantity());
                itemEntity.setCreatedAt(item.getCreatedAt());
                itemEntity.setCreatedBy(item.getCreatedBy());
                itemEntity.setUpdatedAt(item.getUpdatedAt());
                itemEntity.setUpdatedBy(item.getUpdatedBy());
                itemEntity.setDeletedAt(item.getDeletedAt());
                itemEntity.setDeletedBy(item.getDeletedBy());
                return itemEntity;
            }).collect(Collectors.toList()));
        }

        return entity;
    }
}
