package com.animalisfriend.global.common.Converter;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.animalisfriend.domain.pets.entity.PetCategory;
import com.animalisfriend.domain.pets.entity.PetSize;

@Converter
public class PetSizeConverter implements AttributeConverter<PetSize, String> {
	@Override
	public String convertToDatabaseColumn(PetSize attribute) {
		if (Objects.isNull(attribute)) {
			throw new NullPointerException("Enum Converting String - PetSize is null");
		}
		return attribute.toString();
	}

	@Override
	public PetSize convertToEntityAttribute(String dbData) {
		return PetSize.valueOf(dbData);
	}
}
