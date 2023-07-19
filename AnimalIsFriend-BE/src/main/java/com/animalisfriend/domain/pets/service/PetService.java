package com.animalisfriend.domain.pets.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animalisfriend.domain.Image.entity.ImageFile;
import com.animalisfriend.domain.Image.repository.ImageFileRepository;
import com.animalisfriend.domain.adopt.dto.request.AdoptRequestDto;
import com.animalisfriend.domain.pets.dto.request.PetRequestDto;
import com.animalisfriend.domain.pets.dto.response.PetResponseDto;
import com.animalisfriend.domain.pets.entity.PetStatus;
import com.animalisfriend.domain.pets.entity.Pets;
import com.animalisfriend.domain.pets.exception.PetNotFoundException;
import com.animalisfriend.domain.pets.repository.PetRepository;
import com.animalisfriend.domain.users.entity.Users;
import com.animalisfriend.domain.users.exception.UserNotFoundException;
import com.animalisfriend.domain.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {

	private final PetRepository petRepository;
	private final UserRepository userRepository;
	private final ImageFileRepository imageFileRepository;

	@Transactional
	public void petRegister(PetRequestDto.PetRegister dto, Long uid) {


		Users user = userRepository.findById(uid)
			.orElseThrow(() -> new UserNotFoundException());

		Pets pet = petRepository.save(Pets.of(dto, user));

		imageFileRepository.save(ImageFile.of(dto.getImageUrl(), user, pet));
	}

	@Transactional(readOnly = true)
	public List<PetResponseDto.findAllPet> findAllPets(PetRequestDto.findAllPetPagination dto) {

		return petRepository.findAllPet(dto)
			.stream()
			.map(
				pets ->
					PetResponseDto.findAllPet.from(
						pets, pets.getUser(), imageFileRepository.findByPetId(pets.getPetId())
					)
			)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public PetResponseDto.findAllPet findPet(Long petId) {
		Pets pet = petRepository.findById(petId)
			.orElseThrow(() -> new PetNotFoundException());

		ImageFile image = imageFileRepository.findByPetId(pet.getPetId());

		return PetResponseDto.findAllPet.from(pet, pet.getUser(), image);
	}

	@Transactional
	public void updatePetStatus(AdoptRequestDto.updateAdopt dto, Long userId) {


		userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Pets pet = petRepository.findById(dto.getPetId())
			.orElseThrow();

		pet.updatePetStatus(PetStatus.valueOf(dto.getPetStatus()));
	}
}
