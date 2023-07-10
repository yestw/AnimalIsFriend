package com.animalisfriend.global.security.oauth.exception;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.Error.exception.BusinessExceptionHandler;

public class ProviderNotFoundException extends BusinessExceptionHandler {

	public ProviderNotFoundException() {
		super(ErrorCode.PROVIDER_NOT_FOUND);
	}
}
