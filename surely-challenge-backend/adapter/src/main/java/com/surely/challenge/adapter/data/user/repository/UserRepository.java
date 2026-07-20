package com.surely.challenge.adapter.data.user.repository;

import com.surely.challenge.adapter.data.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByDocumentNumber(String documentNumber);
}
