package com.animalisfriend.domain.adopt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalisfriend.domain.adopt.entity.Adopts;

public interface AdoptsRepository extends JpaRepository<Adopts, Long> {
}
