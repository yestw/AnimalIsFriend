package com.animalisfriend.domain.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalisfriend.domain.chat.dto.ChatDto;
import com.animalisfriend.domain.chat.dto.request.ChatRequestDto;
import com.animalisfriend.domain.chat.dto.response.ChatResponseDto;
import com.animalisfriend.domain.chat.service.ChatService;
import com.animalisfriend.global.Error.code.SuccessCode;
import com.animalisfriend.global.common.auth.AuthRequired;
import com.animalisfriend.global.security.jwt.dto.JwtAuthentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController { 

	private final ChatService chatService;

	@PostMapping("/validation")
	@AuthRequired
	public ResponseEntity<Object> checkChatRoom(@RequestBody ChatRequestDto.CheckChatRoom dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		log.info("checkChatRoom : {}", dto.getUserId());

		return ResponseEntity.ok().body(chatService.checkChatRoom(user.userId, dto));
	}

	@PostMapping
	@AuthRequired
	public ResponseEntity<Object> createChatRoom(@AuthenticationPrincipal JwtAuthentication user,
		@RequestBody ChatRequestDto.ChatRoomCreate dto) {
		chatService.createChatRoom(user.userId, dto);

		return ResponseEntity.ok().body(SuccessCode.CHATROOM_CREATE_SUCCESS);
	}

	@GetMapping("/chatroom")
	@AuthRequired
	public ResponseEntity<List<ChatResponseDto.ChatRoomInfo>> getChatRoom(
		@ModelAttribute ChatRequestDto.findChatRoomPagination dto,
		@AuthenticationPrincipal JwtAuthentication user) {

		return ResponseEntity.ok().body(chatService.findChatRoomByUser(dto, user.userId));
	}

	@GetMapping("{chatroomId}")
	@AuthRequired
	public ResponseEntity<List<ChatDto>> getChat(
		@PathVariable("chatroomId") Long chatroomId,
		@ModelAttribute ChatRequestDto.findChatPagination dto,
		@AuthenticationPrincipal JwtAuthentication user) {

		return ResponseEntity.ok().body(chatService.findChatByChatRoom(chatroomId, dto, user.userId));
	}

	@GetMapping("/readChat")
	@AuthRequired
	public void readChat(
		@ModelAttribute ChatRequestDto.ReadChatRequest dto,
		@AuthenticationPrincipal JwtAuthentication user) {

		chatService.readChat(dto, user.userId);
	}
}
