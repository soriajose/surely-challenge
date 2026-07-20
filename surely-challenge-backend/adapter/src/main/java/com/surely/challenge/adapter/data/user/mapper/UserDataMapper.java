package com.surely.challenge.adapter.data.user.mapper;

import com.surely.challenge.adapter.data.user.model.UserEntity;
import com.surely.challenge.user.model.User;

public class UserDataMapper {

    public static User dataCoreMapper(UserEntity userEntity) {
        if (userEntity == null) return null;
        
        User user = User.factoryUser(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getDocumentNumber(),
                userEntity.getEmail(),
                userEntity.isVip()
        );

        user.setId(userEntity.getId());
        user.setCreatedAt(userEntity.getCreatedAt());
        user.setCreatedBy(userEntity.getCreatedBy());
        user.setUpdatedAt(userEntity.getUpdatedAt());
        user.setUpdatedBy(userEntity.getUpdatedBy());
        user.setDeletedAt(userEntity.getDeletedAt());
        user.setDeletedBy(userEntity.getDeletedBy());

        return user;
    }

    public static UserEntity dataEntityMapper(User user) {
        if (user == null) return null;

        return new UserEntity(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDocumentNumber(),
                user.getEmail(),
                user.isVip(),
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getUpdatedAt(),
                user.getUpdatedBy(),
                user.getDeletedAt(),
                user.getDeletedBy()
        );
    }
}
