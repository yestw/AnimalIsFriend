package com.animalisfriend.domain.chat.repository;

import java.util.List;

import com.animalisfriend.domain.chat.dto.request.ChatRequestDto;
import com.animalisfriend.domain.chat.entity.Chat;
import com.animalisfriend.domain.chat.entity.ChatRoom;

public interface ChatCustomRepository{

	List<ChatRoom> findChatRoomByUser(ChatRequestDto.findChatRoomPagination dto, Long userId);

	List<Chat> findChatByChatRoom(ChatRequestDto.findChatPagination dto, Long chatroomId);

}
