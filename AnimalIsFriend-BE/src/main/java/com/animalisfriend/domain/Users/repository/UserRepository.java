package com.animalisfriend.domain.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animalisfriend.domain.users.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByProviderId(String providerId);

}
