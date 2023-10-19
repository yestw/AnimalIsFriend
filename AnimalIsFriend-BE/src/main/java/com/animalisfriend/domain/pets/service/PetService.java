package com.animalisfriend.domain.pets.service;

import java.util.List;

import com.animalisfriend.domain.pets.exception.PetNotMatchException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
	public List<PetResponseDto.findAllPetDto> findAllPets(PetRequestDto.findAllPetPagination dto) {

		return petRepository.findAllPet(dto);
	}

	@Transactional(readOnly = true)
	public PetResponseDto.findAllPetDto findPet(Long petId) {
		Pets pet = petRepository.findById(petId)
			.orElseThrow(() -> new PetNotFoundException());

		Users users = userRepository.findById(pet.getUser().getId())
				.orElseThrow(() -> new UserNotFoundException());

		ImageFile image = imageFileRepository.findByPetId(pet.getPetId());

		return PetResponseDto.findAllPetDto.from(pet, users, image);
	}

	@Transactional
	public void updatePetStatus(AdoptRequestDto.updateAdopt dto, Long userId) {

		userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Pets pet = petRepository.findById(dto.getPetId())
			.orElseThrow();

		pet.updatePetStatus(PetStatus.valueOf(dto.getPetStatus()));
	}

	@Transactional
	public void deletePet(Long userId, Long petId) {
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());

		petRepository.findById(petId)
				.ifPresentOrElse(
						pet -> validatePetForDelete(pet.getPetId(), user.getId()),
						() -> new PetNotFoundException()
				);
	}
	private void validatePetForDelete(Long userId, Long petId) {
		petRepository.findByPetsWhereUserIdAndPetId(userId, petId)
				.ifPresentOrElse(
						pet -> petRepository.delete(pet),
						() -> new PetNotMatchException()
				);
	}

	public void updatePetInfo(Long userId, Long pid, PetRequestDto.petUpdateDto dto) {

		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());

		petRepository.findById(pid)
				.ifPresentOrElse(
						pet -> validatePetForUpdate(user.getId(), pet.getPetId(), dto),
						() -> new PetNotFoundException()
				);
	}

	private void validatePetForUpdate(Long userId, Long petId, PetRequestDto.petUpdateDto dto) {
		petRepository.findByPetsWhereUserIdAndPetId(userId, petId)
				.ifPresentOrElse(
						pet -> pet.petUpdate(dto),
						() -> new PetNotMatchException()
				);
	}
}
