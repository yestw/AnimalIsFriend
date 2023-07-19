package com.animalisfriend.domain.Image.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.animalisfriend.domain.chat.entity.ChatRoom;
import com.animalisfriend.domain.pets.entity.Pets;
import com.animalisfriend.domain.users.entity.Users;
import com.animalisfriend.global.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ImageFile extends BaseEntity {

	 public static final String defaultImageUrl = "imageUrl";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageId;

	@Column(name = "imageUrl")
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users users;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pet_id")
	private Pets pets;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chatroom_id")
	private ChatRoom chatRoom;

	@Builder
	private ImageFile(String imageUrl, Users users, Pets pets, ChatRoom chatRoom) {
		this.imageUrl = imageUrl;
		this.users = users;
		this.pets = pets;
		this.chatRoom = chatRoom;
	}

	public static ImageFile of(String imageUrl, Users users) {
		return ImageFile.builder()
			.imageUrl(imageUrl)
			.users(users)
			.build();
	}

	public static ImageFile of(String imageUrl, Users users, Pets pets) {
		return ImageFile.builder()
			.imageUrl(imageUrl)
			.users(users)
			.pets(pets)
			.build();
	}

	public static ImageFile of(String imageUrl, Users users, ChatRoom chatRoom) {
		return ImageFile.builder()
			.imageUrl(imageUrl)
			.users(users)
			.chatRoom(chatRoom)
			.build();
	}

	public void updateImage(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
