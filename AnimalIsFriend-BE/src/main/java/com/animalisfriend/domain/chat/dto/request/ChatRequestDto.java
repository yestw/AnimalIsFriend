package com.animalisfriend.domain.chat.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class ChatRequestDto {

	@Getter
	public static class CheckChatRoom {
		private Long userId;
	}

	@Getter
	public static class ChatRoomCreate {
		private String chatroomName;
		private Long userId;
		private Long petId;
	}

	@Getter
	@NoArgsConstructor
	public static class findChatRoomPagination {
		private Long chatroomId;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class findChatPagination {
		private Long chatId;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate requestDate;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ReadChatRequest {
		private Long chatroomId;
		private Boolean readChat;
	}
}
