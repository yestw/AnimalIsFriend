package com.animalisfriend.domain.chat.exception;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.Error.exception.BusinessExceptionHandler;

public class YourselfCannotCreateChatRoomException extends BusinessExceptionHandler {

	public YourselfCannotCreateChatRoomException() {
		super(ErrorCode.CANNOT_CREATE_CHATROOM);
	}
}
