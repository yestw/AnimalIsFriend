package com.animalisfriend.domain.pets.repository;

import static com.animalisfriend.domain.pets.entity.QPets.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.animalisfriend.domain.pets.dto.request.PetRequestDto;
import com.animalisfriend.domain.pets.entity.Pets;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PetCustomRepositoryImpl implements PetCustomRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Pets> findAllPet(PetRequestDto.findAllPetPagination dto) {
		List<Pets> result = jpaQueryFactory
			.selectFrom(pets)
			.where(
				ltPetId(dto.getPetId())
			)
			.limit(20)
			.orderBy(pets.petId.desc())
			.fetch();

		return result;
	}

	private BooleanExpression ltPetId(Long petId) {
		if(petId == null) {
			return null;
		}
		return pets.petId.lt(petId);
	}
}
