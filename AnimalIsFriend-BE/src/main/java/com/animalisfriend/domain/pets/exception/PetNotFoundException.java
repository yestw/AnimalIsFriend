package com.animalisfriend.domain.pets.exception;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.Error.exception.BusinessExceptionHandler;

public class PetNotFoundException extends BusinessExceptionHandler {

	public PetNotFoundException() {
		super(ErrorCode.PET_NOT_FOUND);
	}
}
