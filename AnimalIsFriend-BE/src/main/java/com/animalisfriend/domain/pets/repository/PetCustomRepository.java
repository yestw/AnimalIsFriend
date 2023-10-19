package com.animalisfriend.domain.pets.repository;

import java.util.List;

import com.animalisfriend.domain.pets.dto.request.PetRequestDto;
import com.animalisfriend.domain.pets.dto.response.PetResponseDto;
import com.animalisfriend.domain.pets.entity.Pets;

public interface PetCustomRepository {

	List<PetResponseDto.findAllPetDto> findAllPet(PetRequestDto.findAllPetPagination dto);

	List<Pets> findAllPetTest(PetRequestDto.findAllPetPagination dto);
}
