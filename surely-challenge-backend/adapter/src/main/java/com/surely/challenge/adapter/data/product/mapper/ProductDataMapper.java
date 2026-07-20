package com.surely.challenge.adapter.data.product.mapper;

import com.surely.challenge.adapter.data.product.model.ProductEntity;
import com.surely.challenge.product.model.Product;

public class ProductDataMapper {

    public static Product dataEntityToCoreMapper(ProductEntity productEntity) {
        if (productEntity == null) return null;
        Product product = Product.factoryProduct(
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice()
        );
        product.setId(productEntity.getId());
        product.setCreatedAt(productEntity.getCreatedAt());
        product.setCreatedBy(productEntity.getCreatedBy());
        product.setUpdatedAt(productEntity.getUpdatedAt());
        product.setUpdatedBy(productEntity.getUpdatedBy());
        product.setDeletedAt(productEntity.getDeletedAt());
        product.setDeletedBy(productEntity.getDeletedBy());
        return product;
    }

    public static ProductEntity dataCoreToEntityMapper(Product product) {
        if (product == null) return null;
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getCreatedBy(),
                product.getUpdatedAt(),
                product.getUpdatedBy(),
                product.getDeletedAt(),
                product.getDeletedBy()
        );
    }
}
