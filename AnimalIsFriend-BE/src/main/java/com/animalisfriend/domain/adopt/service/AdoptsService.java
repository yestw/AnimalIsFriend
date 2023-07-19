package com.animalisfriend.domain.adopt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animalisfriend.domain.adopt.dto.request.AdoptRequestDto;
import com.animalisfriend.domain.adopt.entity.Adopts;
import com.animalisfriend.domain.adopt.repository.AdoptsRepository;
import com.animalisfriend.domain.pets.entity.PetStatus;
import com.animalisfriend.domain.pets.entity.Pets;
import com.animalisfriend.domain.pets.repository.PetRepository;
import com.animalisfriend.domain.users.entity.Users;
import com.animalisfriend.domain.users.exception.UserNotFoundException;
import com.animalisfriend.domain.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdoptsService {

	private final AdoptsRepository adoptsRepository;
	private final PetRepository petRepository;
	private final UserRepository userRepository;

	@Transactional
	public void adoptSuccess(AdoptRequestDto.updateAdopt dto, Long userId) {

		Users user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Pets pet = petRepository.findById(dto.getPetId())
			.orElseThrow();

		pet.updatePetStatus(PetStatus.ADOPTION_COMPLETE);

		adoptsRepository.save(Adopts.of(user, pet));
	}
}
