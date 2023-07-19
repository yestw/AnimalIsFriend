package com.animalisfriend.domain.adopt.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AdoptRequestDto {

	@Getter
	@NoArgsConstructor
	public static class updateAdopt {
		private Long petId;
		private String petStatus;
	}
}
