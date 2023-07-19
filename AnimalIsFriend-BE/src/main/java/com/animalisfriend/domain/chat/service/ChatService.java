package com.animalisfriend.domain.chat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.html.Option;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animalisfriend.domain.Image.repository.ImageFileRepository;
import com.animalisfriend.domain.chat.dto.ChatDto;
import com.animalisfriend.domain.chat.dto.request.ChatRequestDto;
import com.animalisfriend.domain.chat.dto.response.ChatResponseDto;
import com.animalisfriend.domain.chat.entity.Chat;
import com.animalisfriend.domain.chat.entity.ChatRoom;
import com.animalisfriend.domain.chat.exception.ChatRoomAlreadyExistException;
import com.animalisfriend.domain.chat.exception.YourselfCannotCreateChatRoomException;
import com.animalisfriend.domain.chat.repository.ChatCustomRepository;
import com.animalisfriend.domain.chat.repository.ChatRepository;
import com.animalisfriend.domain.chat.repository.ChatRoomRepository;
import com.animalisfriend.domain.pets.entity.Pets;
import com.animalisfriend.domain.pets.repository.PetRepository;
import com.animalisfriend.domain.users.entity.Users;
import com.animalisfriend.domain.users.exception.UserNotFoundException;
import com.animalisfriend.domain.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final UserRepository userRepository;
	private final ChatCustomRepository chatCustomRepository;
	private final PetRepository petRepository;
	private final ImageFileRepository imageFileRepository;

	private boolean readFlag = false;

	public boolean checkChatRoom(Long userId, ChatRequestDto.CheckChatRoom dto) {
		Users creator = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Users invited_user = userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new UserNotFoundException());

		return chatRoomRepository.findChatRoom(creator.getId(), invited_user.getId()).isPresent();
	}

	//채팅방 생성
	@Transactional
	public void createChatRoom(Long userId, ChatRequestDto.ChatRoomCreate dto) {
		Users creator = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Users invited_user = userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new UserNotFoundException());

		Pets pet = petRepository.findById(dto.getPetId())
			.orElseThrow();

		if(creator.getId() == invited_user.getId()) {
			throw new YourselfCannotCreateChatRoomException();
		}

		chatRoomRepository.findChatRoom(creator.getId(), invited_user.getId())
				.ifPresentOrElse(
					chatRoom -> {
						throw new ChatRoomAlreadyExistException();
					},
					() -> chatRoomRepository.save(ChatRoom.of(dto.getChatroomName(), creator, invited_user, pet))
				);
	}

	@Transactional(readOnly = true)
	public List<ChatResponseDto.ChatRoomInfo> findChatRoomByUser(ChatRequestDto.findChatRoomPagination dto, Long userId) {

		return chatCustomRepository.findChatRoomByUser(dto, userId)
			.stream()
			.map(
				chatRoom ->
					ChatResponseDto.ChatRoomInfo.from(
							chatRoom,
							chatRepository.unReadChatCount(chatRoom.getChatroomId(), userId),
							imageFileRepository.findByPetId(chatRoom.getPet().getPetId()),
							petRepository.findPetsByPetIdAndUserId(chatRoom.getPet().getPetId(), userId)
						)
			)
			.collect(Collectors.toList());
	}

	@Transactional
	public ChatDto sendChat(ChatDto dto, Long roomId) {

		ChatRoom chatRoom = chatRoomRepository.findById(roomId)
				.orElseThrow();

		Users user = userRepository.findById(dto.getSenderId())
				.orElseThrow(() -> new UserNotFoundException());


		log.info("result : {}", dto.getReadChat());
		log.info("result : {}", dto.getSenderId() == user.getId());

		if(dto.getSenderId() != user.getId()) {
			if(dto.getReadChat()) {
				readFlag = !readFlag;
			}
		}

		Chat chat = chatRepository.save(Chat.of(dto.getMessage(), chatRoom, user, dto.getReadChat()));

		return ChatDto.from(chat);
	}

	@Transactional(readOnly = true)
	public List<ChatDto> findChatByChatRoom(Long chatroomId, ChatRequestDto.findChatPagination dto, Long userId) {

		userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());

		chatRoomRepository.findById(chatroomId)
			.orElseThrow();

		return chatCustomRepository.findChatByChatRoom(dto, chatroomId)
			.stream()
			.map(
				chat -> ChatDto.from(chat)
			).collect(Collectors.toList());
	}

	@Transactional
	public void readChat(ChatRequestDto.ReadChatRequest dto, Long userId) {

		log.info("service readChat 실행되었니?");

		userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());

		ChatRoom chatRoom = chatRoomRepository.findById(dto.getChatroomId())
			.orElseThrow();

		if(dto.getReadChat()) {
			chatRepository.readChatUpdate(chatRoom.getChatroomId())
				.stream()
				.forEach(
					chat -> chat.readChat()
				);
		}
	}
}
