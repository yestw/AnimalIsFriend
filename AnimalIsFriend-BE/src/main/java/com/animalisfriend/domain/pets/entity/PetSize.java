package com.animalisfriend.domain.pets.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PetSize {

	SMALL("소형견"),
	MIDDLE("중형견"),
	BIG("대형견"),

	X("고양이")
	;

	private final String petSize;
}
