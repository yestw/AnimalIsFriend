package com.animalisfriend.domain.pets.dto.response;

import com.animalisfriend.domain.Image.entity.ImageFile;
import com.animalisfriend.domain.pets.entity.*;
import com.animalisfriend.domain.users.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PetResponseDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class findAllPetDto {
		private Long petId;
		private String petName;
		private String petGender;
		private String petBirth;
		private String petAcquire;
		private String petFeed;
		private String petBreed;
		private String petDescription;
		private PetCategory petCategory;
		private PetSize petSize;
		private PetStatus petStatus;
		private String userNickname;
		private Long id;
		private String imageUrl;

		public static findAllPetDto from(Pets pet, Users user, ImageFile image) {
			return new findAllPetDto(
				pet.getPetId(),
				pet.getPetName(),
				pet.getPetGender(),
				pet.getPetBirth(),
				pet.getPetAcquire(),
				pet.getPetFeed(),
				pet.getPetBreed(),
				pet.getPetDescription(),
				pet.getPetCategory(),
				pet.getPetSize(),
				pet.getPetStatus(),
				user.getUserNickname(),
				user.getId(),
				image.getImageUrl()==null?"": image.getImageUrl()
			);
		}
	}
}
