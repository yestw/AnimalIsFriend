package com.animalisfriend.domain.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.animalisfriend.domain.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	@Query("select r from ChatRoom r where r.creator.id = :creator and r.invited_user.id = :invited_user")
	Optional<ChatRoom> findChatRoom(@Param("creator") Long creator, @Param("invited_user") Long invited_user);

}
