package com.animalisfriend.domain.pets.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.animalisfriend.domain.pets.entity.Pets;

@Repository
public interface PetRepository extends JpaRepository<Pets, Long>, PetCustomRepository{

	@Query("select count(p) from Pets p where p.petId = :pid and p.user.id = :uid")
	Integer findPetsByPetIdAndUserId(@Param("pid") Long pid, @Param("uid") Long uid);

}
