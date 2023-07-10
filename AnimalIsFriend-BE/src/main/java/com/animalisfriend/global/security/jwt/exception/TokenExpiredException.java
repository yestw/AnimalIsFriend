package com.animalisfriend.global.security.jwt.exception;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.Error.exception.BusinessExceptionHandler;

public class TokenExpiredException extends BusinessExceptionHandler {
	public TokenExpiredException() {
		super(ErrorCode.TOKEN_EXPIRED);
	}
}
