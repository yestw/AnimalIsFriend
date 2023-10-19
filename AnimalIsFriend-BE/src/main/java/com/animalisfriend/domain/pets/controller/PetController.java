package com.animalisfriend.domain.pets.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.animalisfriend.domain.adopt.dto.request.AdoptRequestDto;
import com.animalisfriend.domain.pets.dto.request.PetRequestDto;
import com.animalisfriend.domain.pets.dto.response.PetResponseDto;
import com.animalisfriend.domain.pets.service.PetService;
import com.animalisfriend.global.Error.code.SuccessCode;
import com.animalisfriend.global.common.auth.AuthRequired;
import com.animalisfriend.global.security.jwt.dto.JwtAuthentication;

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
		petService.petRegister(dto, user.userId);

		return ResponseEntity.ok().body(SuccessCode.PET_REGISTER_SUCCESS);
	}

	@GetMapping
	public ResponseEntity<List<PetResponseDto.findAllPetDto>> getAllPets(@ModelAttribute PetRequestDto.findAllPetPagination dto) {
		return ResponseEntity.ok().body(petService.findAllPets(dto));
	}

	@GetMapping("/{pid}")
	public ResponseEntity<PetResponseDto.findAllPetDto> getPet(@PathVariable("pid") Long pid) {
		return ResponseEntity.ok().body(petService.findPet(pid));
	}

	@PatchMapping
	@AuthRequired
	public ResponseEntity<Object> updatePetStatus(@RequestBody AdoptRequestDto.updateAdopt dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		petService.updatePetStatus(dto, user.userId);

		return ResponseEntity.ok().body(SuccessCode.UPDATE_SUCCESS);
	}

	@DeleteMapping("/{pid}")
	public ResponseEntity<Object> deletePet(@AuthenticationPrincipal JwtAuthentication user,
											@PathVariable("pid") Long pid) {
		petService.deletePet(user.userId, pid);

		return ResponseEntity.ok().body(SuccessCode.DELETE_SUCCESS);
	}

	@PatchMapping("/pid")
	public ResponseEntity<Object> updatePet(@AuthenticationPrincipal JwtAuthentication user,
											@PathVariable("pid") Long pid,
											@RequestBody PetRequestDto.petUpdateDto dto) {
		petService.updatePetInfo(user.userId, pid, dto);

		return ResponseEntity.ok().body(SuccessCode.UPDATE_SUCCESS);
	}


}
