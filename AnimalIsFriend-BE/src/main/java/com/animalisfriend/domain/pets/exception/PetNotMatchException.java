package com.animalisfriend.domain.pets.exception;

import com.animalisfriend.global.Error.code.ErrorCode;
import com.animalisfriend.global.Error.exception.BusinessExceptionHandler;

public class PetNotMatchException extends BusinessExceptionHandler {

    public PetNotMatchException() {
        super(ErrorCode.PET_NOT_MATCH);
    }
}
