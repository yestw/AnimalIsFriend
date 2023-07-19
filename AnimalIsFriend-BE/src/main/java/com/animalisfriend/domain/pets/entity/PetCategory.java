package com.animalisfriend.domain.pets.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PetCategory {

	DOG("강아지"),
	CAT("고양이"),

	;

	private final String petCategory;
}
