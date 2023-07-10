package com.animalisfriend.domain.Users.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.animalisfriend.domain.Users.dto.UserSignupRequestDto;
import com.animalisfriend.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Users extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 5)
	private String userName;
	@Column(length = 12)
	private String userHp;
	@Column
	private String userAddress;
	@Column(length = 20)
	private String userNickname;
	@Column
	private String userGender;
	@Enumerated(EnumType.STRING)
	@Column
	private Provider provider;
	@Column
	private String providerId;
	@Enumerated(EnumType.STRING)
	@Column
	private UserRole role;

	@Builder
	public Users(String userName, String userHp, String userAddress,
		String userNickname, String userGender, Provider provider, String providerId, UserRole role) {
		this.userName = userName;
		this.userHp = userHp;
		this.userAddress = userAddress;
		this.userNickname = userNickname;
		this.userGender = userGender;
		this.provider = provider;
		this.providerId = providerId;
		this.role = role;
	}

	public void userUpdate(UserSignupRequestDto dto) {
		this.userName = dto.getUserName();
		this.userHp = dto.getUserHp();
		this.userGender = dto.getUserGender();
		this.userAddress = dto.getUserAddress();
		this.role = UserRole.USER;
	}
}
