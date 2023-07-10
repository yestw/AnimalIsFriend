package com.animalisfriend.domain.Users.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animalisfriend.domain.Users.dto.UserSignupRequestDto;
import com.animalisfriend.domain.Users.exception.UserNotFoundException;
import com.animalisfriend.domain.Users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;


	@Transactional
	public void signup(UserSignupRequestDto dto, Long uid) {
		userRepository.findById(uid)
			.ifPresentOrElse(
				user -> user.userUpdate(dto),
				() -> {
					throw new UserNotFoundException();
				}
			);
	}
}
