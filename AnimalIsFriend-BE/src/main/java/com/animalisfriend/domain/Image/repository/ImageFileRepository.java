package com.animalisfriend.domain.Image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.animalisfriend.domain.Image.entity.ImageFile;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {


	@Query("select i from ImageFile i where i.pets.petId = :petId")
	ImageFile findByPetId(@Param("petId") Long petId);
}
