package com.surely.challenge.adapter.data.promotionaldate.repository;

import com.surely.challenge.adapter.data.promotionaldate.model.PromotionalDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PromotionalDateRepository extends JpaRepository<PromotionalDateEntity, Long> {

    boolean existsByDate(LocalDate date);
}
