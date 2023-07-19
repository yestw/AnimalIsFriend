package com.animalisfriend.domain.pets.repository;

import java.util.List;

import com.animalisfriend.domain.pets.dto.request.PetRequestDto;
import com.animalisfriend.domain.pets.entity.Pets;

public interface PetCustomRepository {

	List<Pets> findAllPet(PetRequestDto.findAllPetPagination dto);
}
