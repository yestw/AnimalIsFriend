package com.animalisfriend.domain.chat.exception;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.Error.exception.BusinessExceptionHandler;

public class ChatRoomAlreadyExistException extends BusinessExceptionHandler {

	public ChatRoomAlreadyExistException() {
		super(ErrorCode.CHATROOM_ALREADY_EXIST);
	}
}
