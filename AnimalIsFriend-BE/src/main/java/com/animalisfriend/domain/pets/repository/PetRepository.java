package com.animalisfriend.domain.pets.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.animalisfriend.domain.pets.entity.Pets;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pets, Long>, PetCustomRepository{

	@Query("select count(p) from Pets p where p.petId = :pid and p.user.id = :uid")
	Integer findPetsByPetIdAndUserId(@Param("pid") Long pid, @Param("uid") Long uid);

	@Query("select p from Pets p where p.petId = :petId and p.user.id = :userId")
	Optional<Pets> findByPetsWhereUserIdAndPetId(@Param("petId")Long petId, @Param("userId") Long userId);

}
