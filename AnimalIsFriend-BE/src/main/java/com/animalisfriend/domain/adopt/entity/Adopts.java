package com.animalisfriend.domain.adopt.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.animalisfriend.domain.pets.entity.Pets;
import com.animalisfriend.domain.users.entity.Users;
import com.animalisfriend.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Adopts extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adoptId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pet_id")
	private Pets pet;

	@Builder
	public Adopts(Users user, Pets pet) {
		this.user = user;
		this.pet = pet;
	}

	public static Adopts of(Users user, Pets pet) {
		return Adopts.builder()
			.user(user)
			.pet(pet)
			.build();
	}
}
