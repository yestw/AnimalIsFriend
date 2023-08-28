package com.animalisfriend.domain.pets.dto.request;

import com.animalisfriend.domain.pets.entity.PetCategory;
import com.animalisfriend.domain.pets.entity.PetSize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PetRequestDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PetRegister {
		private String petName;
		private String petGender;
		private String petBirth;
		private String petAcquire;
		private String petBreed;
		private String petDescription;
		private String petFeed;
		private PetCategory petCategory;
		private PetSize petSize;
		private String imageUrl;
	}

	@Getter
	@NoArgsConstructor
	public static class findAllPetPagination {
		private Long petId;
		private PetSize petSize;
		private PetCategory petCategory;

		@Builder
		public findAllPetPagination(Long petId, PetSize petSize, PetCategory petCategory) {
			this.petId = petId;
			this.petSize = petSize;
			this.petCategory = petCategory;
		}
	}
}
