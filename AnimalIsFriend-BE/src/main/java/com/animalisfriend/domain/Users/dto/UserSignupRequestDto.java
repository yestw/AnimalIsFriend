package com.animalisfriend.domain.Users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {

	private String userName;
	private String userHp;
	private String userGender;
	private String userAddress;
}
