package com.animalisfriend.domain.chat.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.animalisfriend.domain.pets.entity.Pets;
import com.animalisfriend.domain.users.entity.Users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chatroom_id")
	private Long chatroomId;

	private String chatroomName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Users creator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invited_user_id")
	private Users invited_user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pet_id")
	private Pets pet;

	@Builder
	private ChatRoom(String chatroomName, Users creator, Users invited_user, Pets pet) {
		this.chatroomName = chatroomName;
		this.creator = creator;
		this.invited_user = invited_user;
		this.pet = pet;
	}

	public static ChatRoom of(String chatroomName, Users creator, Users invited_user, Pets pet) {
		return ChatRoom.builder()
			.chatroomName(chatroomName)
			.creator(creator)
			.invited_user(invited_user)
			.pet(pet)
			.build();
	}
}
