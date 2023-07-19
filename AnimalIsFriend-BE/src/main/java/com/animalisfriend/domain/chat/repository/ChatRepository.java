package com.animalisfriend.domain.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.animalisfriend.domain.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	@Query("select c from Chat c where c.chatRoom.chatroomId = :chatroomId and c.readFlag = false")
	List<Chat> readChatUpdate(@Param("chatroomId") Long chatroomId);


	@Query("select count(c) " +
			"from Chat c " +
			"where c.chatRoom.chatroomId = :chatroomId " +
			"and c.readFlag = false " +
			"and c.user.id <> :userId ")
	int unReadChatCount(@Param("chatroomId") Long chatroomId, @Param("userId") Long userId);
}
