package com.animalisfriend.domain.Image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.animalisfriend.domain.Image.entity.ImageFile;

import java.util.Optional;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {


	@Query("select i from ImageFile i where i.pets.petId = :petId")
	ImageFile findByPetId(@Param("petId") Long petId);


	@Query("select i from ImageFile  i where i.users.id = :userId")
	Optional<ImageFile> findByUserId(@Param("userId") Long userId);
}
