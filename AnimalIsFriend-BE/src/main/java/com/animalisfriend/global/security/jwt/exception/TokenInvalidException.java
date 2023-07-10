package com.animalisfriend.global.security.jwt.exception;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.Error.exception.BusinessExceptionHandler;

public class TokenInvalidException extends BusinessExceptionHandler {
	public TokenInvalidException() {
		super(ErrorCode.TOKEN_INVALID);
	}
}
