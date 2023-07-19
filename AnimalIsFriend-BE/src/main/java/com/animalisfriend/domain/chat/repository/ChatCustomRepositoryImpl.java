package com.animalisfriend.domain.chat.repository;

import static com.animalisfriend.domain.chat.entity.QChat.*;
import static com.animalisfriend.domain.chat.entity.QChatRoom.*;
import static com.querydsl.jpa.JPAExpressions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.animalisfriend.domain.chat.dto.request.ChatRequestDto;
import com.animalisfriend.domain.chat.dto.response.ChatResponseDto;
import com.animalisfriend.domain.chat.entity.Chat;
import com.animalisfriend.domain.chat.entity.ChatRoom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatCustomRepositoryImpl implements ChatCustomRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ChatRoom> findChatRoomByUser(ChatRequestDto.findChatRoomPagination dto, Long userId) {

		return jpaQueryFactory
			.selectFrom(chatRoom)
			.where(
				chatRoom.invited_user.id.eq(userId)
					.or(chatRoom.creator.id.eq(userId))
					,(ltChatroomId(dto.getChatroomId()))
			)
			.orderBy(chatRoom.chatroomId.desc())
			.limit(10)
			.fetch();
	}

	@Override
	public List<Chat> findChatByChatRoom(ChatRequestDto.findChatPagination dto, Long chatroomId) {

		LocalDate requestDate = LocalDate.from(dto.getRequestDate());

		return jpaQueryFactory
			.selectFrom(chat)
			.where(
				chat.chatRoom.chatroomId.eq(chatroomId),
				ltChatId(dto.getChatId()),
				chat.createdDate.between(
					requestDate.minusDays(7).atStartOfDay(),
					requestDate.plusDays(1).atStartOfDay())
			)
			.orderBy(chat.chatId.desc())
			.limit(30)
			.fetch();
	}

	private BooleanExpression ltChatroomId(Long chatroomId) {
		if(chatroomId == null) {
			return null;
		}
		return chatRoom.chatroomId.lt(chatroomId);
	}

	private BooleanExpression ltChatId(Long chatId) {
		if(chatId == null) {
			return null;
		}
		return chat.chatId.lt(chatId);
	}
}
