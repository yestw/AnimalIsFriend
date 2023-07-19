package com.animalisfriend.domain.users.exception;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.Error.exception.BusinessExceptionHandler;

public class UserNotFoundException extends BusinessExceptionHandler {

	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}
}
