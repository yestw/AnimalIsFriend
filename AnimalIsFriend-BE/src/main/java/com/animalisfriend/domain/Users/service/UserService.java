package com.animalisfriend.domain.users.service;

import com.animalisfriend.domain.Image.entity.ImageFile;
import com.animalisfriend.domain.Image.repository.ImageFileRepository;
import com.animalisfriend.domain.users.dto.UserUpdateRequestDto;
import com.animalisfriend.domain.users.entity.Users;
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
	private final ImageFileRepository imageFileRepository;
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

	@Transactional
	public void withdrawalMembership(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(
				user -> userRepository.deleteById(userId),
				() -> new UserNotFoundException()
		);
	}

	@Transactional
	public void updateUser(UserUpdateRequestDto dto, Long userId) {
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());

		imageFileRepository.findByUserId(userId)
				.ifPresentOrElse(
						imageFile -> imageFile.updateImage(dto.getImageUrl()),
						() -> uploadImage(dto.getImageUrl(), user)
				);
		user.userInfoUpdate(dto);
	}

	private void uploadImage(String imageUrl, Users user) {
		imageFileRepository.save(ImageFile.of(imageUrl, user));
	}
}
