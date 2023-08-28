package com.animalisfriend.domain.pets.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.context.annotation.Description;

import com.animalisfriend.domain.pets.dto.request.PetRequestDto;
import com.animalisfriend.domain.users.entity.Users;
import com.animalisfriend.global.common.Converter.PetCategoryConverter;
import com.animalisfriend.global.common.Converter.PetSizeConverter;
import com.animalisfriend.global.common.Converter.PetStatusConverter;
import com.animalisfriend.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Description("펫 엔티티")
public class Pets extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long petId;

	@Column(name = "pet_name")
	private String petName;

	@Column(name = "pet_gender")
	private String petGender;

	@Column(name = "pet_birth")
	private String petBirth;

	@Column(name = "pet_acquire")
	private String petAcquire;

	@Column(name = "pet_feed")
	private String petFeed;

	@Column(name = "pet_breed")
	private String petBreed;

	@Column(name = "pet_description")
	private String petDescription;

	@Column(name = "pet_category")
	@Convert(converter = PetCategoryConverter.class)
	private PetCategory petCategory;

	@Column(name = "pet_size")
	@Convert(converter = PetSizeConverter.class)
	private PetSize petSize;

	@Column(name = "pet_status")
	@Convert(converter = PetStatusConverter.class)
	private PetStatus petStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users user;


	@Builder
	private Pets (String petName, String petGender, String petBirth, String petAcquire, String petBreed,
		String petDescription ,String petFeed, PetCategory petCategory, PetSize petSize,
		PetStatus petStatus, Users user) {
		this.petName = petName;
		this.petGender = petGender;
		this.petBirth = petBirth;
		this.petAcquire = petAcquire;
		this.petBreed = petBreed;
		this.petDescription = petDescription;
		this.petFeed = petFeed;
		this.petCategory = petCategory;
		this.petSize = petSize;
		this.petStatus = petStatus;
		this.user = user;
	}

	public static Pets of(PetRequestDto.PetRegister dto, Users user) {
		return Pets.builder()
			.petName(dto.getPetName())
			.petGender(dto.getPetGender())
			.petBirth(dto.getPetBirth())
			.petAcquire(dto.getPetAcquire())
			.petBreed(dto.getPetBreed())
			.petDescription(dto.getPetDescription())
			.petFeed(dto.getPetFeed())
			.petCategory(dto.getPetCategory())
			.petSize(dto.getPetSize())
			.petStatus(PetStatus.NOT_ADOPTED)
			.user(user)
			.build();
	}

	public void updatePetStatus(PetStatus petStatus) {
		this.petStatus = petStatus;
	}
}
