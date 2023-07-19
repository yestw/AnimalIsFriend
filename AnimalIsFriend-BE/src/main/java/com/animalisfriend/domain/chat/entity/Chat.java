package com.animalisfriend.domain.chat.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.animalisfriend.domain.users.entity.Users;
import com.animalisfriend.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Chat extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatId;

	private String message;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chatroom_id")
	private ChatRoom chatRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users user;

	private boolean readFlag;

	@Builder
	private Chat(String message, ChatRoom chatRoom, Users user, boolean readFlag) {
		this.message = message;
		this.chatRoom = chatRoom;
		this.user = user;
		this.readFlag = readFlag;
	}

	public static Chat of(String message, ChatRoom chatRoom, Users user, boolean readFlag) {
		return Chat.builder()
			.message(message)
			.chatRoom(chatRoom)
			.user(user)
			.readFlag(readFlag)
			.build();
	}

	public void readChat() {
		this.readFlag = !readFlag;
	}

}
