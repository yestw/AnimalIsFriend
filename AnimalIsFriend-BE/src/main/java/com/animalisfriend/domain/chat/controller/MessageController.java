package com.animalisfriend.domain.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalisfriend.domain.chat.dto.ChatDto;
import com.animalisfriend.domain.chat.service.ChatService;
import com.animalisfriend.global.common.auth.AuthRequired;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

	private final ChatService chatService;
	private final SimpMessagingTemplate template;

	//클라이언트에서 /pub 로 메시지를 발행
	@MessageMapping("/queue/{roomId}")
	@AuthRequired
	public void message(@Payload ChatDto dto, @DestinationVariable("roomId") Long roomId) {
		log.info("roomId : {}", dto.toString());
		log.info("roomId : {}", roomId);

		ChatDto chat = chatService.sendChat(dto, roomId);

		log.info("chat : {}", chat.toString());

		//메시지에 정의된 채널 id에 메시지를 보냄, /sub/channel/채널아이디 에 구독중인 클라이언트에게 메시지를 보냅니다.
		template.convertAndSend("/queue/" + roomId, chat);
	}
}
