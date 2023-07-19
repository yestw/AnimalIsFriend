package com.animalisfriend.domain.users.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animalisfriend.domain.users.dto.UserSignupRequestDto;
import com.animalisfriend.domain.users.exception.UserNotFoundException;
import com.animalisfriend.domain.users.repository.UserRepository;

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
