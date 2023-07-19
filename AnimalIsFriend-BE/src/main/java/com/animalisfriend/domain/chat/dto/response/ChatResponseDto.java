package com.animalisfriend.domain.chat.dto.response;

import com.animalisfriend.domain.Image.entity.ImageFile;
import com.animalisfriend.domain.chat.entity.ChatRoom;
import com.animalisfriend.domain.pets.entity.PetStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ChatResponseDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ChatRoomInfo {
		private Long chatroomId;
		private String chatroomName;
		private String creatorName;
		private String invitedName;
		private Long creatorId;
		private Long invitedId;
		private int unReadChatCount;
		private Long petId;
		private String imageUrl;
		private PetStatus petStatus;
		private Integer isPetOwner;


		public static ChatRoomInfo from(ChatRoom chatRoom, Integer unReadChatCount, ImageFile image, Integer isPetOwner) {
			return ChatRoomInfo.builder()
				.chatroomId(chatRoom.getChatroomId())
				.chatroomName(chatRoom.getChatroomName())
				.creatorName(chatRoom.getCreator().getUserNickname())
				.invitedName(chatRoom.getInvited_user().getUserNickname())
				.creatorId(chatRoom.getCreator().getId())
				.invitedId(chatRoom.getInvited_user().getId())
				.unReadChatCount(unReadChatCount == null ? 0 : unReadChatCount)
				.petId(chatRoom.getPet().getPetId())
				.petStatus(chatRoom.getPet().getPetStatus())
				.imageUrl(image.getImageUrl())
				.isPetOwner(isPetOwner)
				.build();
		}
	}
}
