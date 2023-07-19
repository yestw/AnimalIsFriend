package com.animalisfriend.global.common.Converter;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.animalisfriend.domain.pets.entity.PetSize;
import com.animalisfriend.domain.pets.entity.PetStatus;

@Converter
public class PetStatusConverter implements AttributeConverter<PetStatus, String> {
	@Override
	public String convertToDatabaseColumn(PetStatus attribute) {
		if (Objects.isNull(attribute)) {
			throw new NullPointerException("Enum Converting String - PetStatus is null");
		}
		return attribute.toString();
	}

	@Override
	public PetStatus convertToEntityAttribute(String dbData) {
		return PetStatus.valueOf(dbData);
	}
}
