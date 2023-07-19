package com.animalisfriend.domain.chat.dto;

import java.time.LocalDateTime;

import com.animalisfriend.domain.chat.entity.Chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ChatDto {

	private Long chatId;
	private String senderName;
	private Long senderId;
	private String creatorName;
	private String message;
	private LocalDateTime createdDate;
	private Boolean readChat;

	@Builder
	public ChatDto(Long chatId, String senderName, Long senderId,
		String creatorName, String message, LocalDateTime createdDate, Boolean readChat) {
		this.chatId = chatId;
		this.senderName = senderName;
		this.senderId = senderId;
		this.creatorName = creatorName;
		this.message = message;
		this.createdDate = createdDate;
		this.readChat = readChat;
	}

	public static ChatDto from(Chat chat) {
		return ChatDto.builder()
			.chatId(chat.getChatId())
			.senderName(chat.getUser().getUserNickname())
			.senderId(chat.getUser().getId())
			.creatorName(chat.getChatRoom().getCreator().getUserName())
			.message(chat.getMessage())
			.createdDate(chat.getCreatedDate())
			.readChat(chat.isReadFlag())
			.build();
	}
}
