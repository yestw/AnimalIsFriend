package com.animalisfriend.domain.pets.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalisfriend.domain.adopt.dto.request.AdoptRequestDto;
import com.animalisfriend.domain.pets.dto.request.PetRequestDto;
import com.animalisfriend.domain.pets.dto.response.PetResponseDto;
import com.animalisfriend.domain.pets.service.PetService;
import com.animalisfriend.global.Error.code.SuccessCode;
import com.animalisfriend.global.common.auth.AuthRequired;
import com.animalisfriend.global.security.jwt.dto.JwtAuthentication;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/pet")
@RequiredArgsConstructor
@Slf4j
public class PetController {

	private final PetService petService;

	@PostMapping
	@AuthRequired
	public ResponseEntity<Object> registerPet(@RequestBody PetRequestDto.PetRegister dto,
		@AuthenticationPrincipal JwtAuthentication user) {

		log.info("imageurl : {}", dto.getImageUrl());

		petService.petRegister(dto, user.userId);

		return ResponseEntity.ok().body(SuccessCode.PET_REGISTER_SUCCESS);
	}

	@GetMapping
	public ResponseEntity<List<PetResponseDto.findAllPet>> getAllPets(@ModelAttribute PetRequestDto.findAllPetPagination dto) {
		return ResponseEntity.ok().body(petService.findAllPets(dto));
	}

	@GetMapping("/{pid}")
	public ResponseEntity<PetResponseDto.findAllPet> getPet(@PathVariable("pid") Long pid) {
		return ResponseEntity.ok().body(petService.findPet(pid));
	}

	@PatchMapping
	@AuthRequired
	public ResponseEntity<Object> updatePetStatus(@RequestBody AdoptRequestDto.updateAdopt dto,
		@AuthenticationPrincipal JwtAuthentication user) {

		log.info("status : {}", dto.getPetStatus());

		petService.updatePetStatus(dto, user.userId);

		return ResponseEntity.ok().body(SuccessCode.UPDATE_SUCCESS);
	}
}
