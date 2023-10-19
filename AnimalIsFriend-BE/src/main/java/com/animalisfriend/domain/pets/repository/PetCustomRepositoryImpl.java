package com.animalisfriend.domain.pets.repository;

import static com.animalisfriend.domain.pets.entity.QPets.*;
import static com.animalisfriend.domain.users.entity.QUsers.users;
import static com.animalisfriend.domain.Image.entity.QImageFile.imageFile;

import java.util.List;

import com.animalisfriend.domain.pets.dto.response.PetResponseDto;
import com.animalisfriend.domain.pets.entity.Pets;
import com.querydsl.core.types.Projections;
import org.springframework.stereotype.Repository;

import com.animalisfriend.domain.pets.dto.request.PetRequestDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PetCustomRepositoryImpl implements PetCustomRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<PetResponseDto.findAllPetDto> findAllPet(PetRequestDto.findAllPetPagination dto) {

		List<PetResponseDto.findAllPetDto> result = jpaQueryFactory
			.select(
					Projections.fields(
							PetResponseDto.findAllPetDto.class,
							pets.petId, pets.petName, pets.petBirth,
							pets.petAcquire, pets.petFeed, pets.petBreed, pets.petDescription,
							pets.petCategory, pets.petSize, pets.petStatus,pets.petGender,
							pets.user.userNickname, pets.user.id,
							imageFile.imageUrl
					)
			).from(pets)
				.leftJoin(users).on(pets.user.id.eq(users.id))
				.leftJoin(imageFile).on(pets.petId.eq(imageFile.pets.petId)).fetchJoin()
			.where(
				ltPetId(dto.getPetId())
			)
			.limit(20)
			.orderBy(pets.petId.desc())
			.fetch();

		return result;
	}

	@Override
	public List<Pets> findAllPetTest(PetRequestDto.findAllPetPagination dto) {

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
