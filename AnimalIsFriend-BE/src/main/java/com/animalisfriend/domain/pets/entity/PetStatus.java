package com.animalisfriend.domain.pets.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PetStatus {

	NOT_ADOPTED("미입양"),
	ADOPTION_WAITING("입양 대기중"),
	ADOPTION_COMPLETE("입양 완료")

	;

	private final String petStatus;
}
