package com.animalisfriend.domain.Users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalisfriend.domain.Users.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByProviderId(String providerId);
}
