package com.animalisfriend.global.common.Converter;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.animalisfriend.domain.pets.entity.PetCategory;

@Converter
public class PetCategoryConverter implements AttributeConverter<PetCategory, String> {

	@Override
	public String convertToDatabaseColumn(PetCategory attribute) {

		if (Objects.isNull(attribute)) {
			throw new NullPointerException("Enum Converting String - PetCategory is null");
		}
		return attribute.toString();
	}

	@Override
	public PetCategory convertToEntityAttribute(String dbData) {
		return PetCategory.valueOf(dbData);
	}
}
