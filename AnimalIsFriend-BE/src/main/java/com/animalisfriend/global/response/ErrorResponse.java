package com.animalisfriend.global.response;


import com.animalisfriend.global.Error.code.ErrorCode;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Global Exception Handler에서 발생한 에러에 대한 응답 처리를 관리
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	private int status;                 // 에러 상태 코드
	private String divisionCode;        // 에러 구분 코드
	private String resultMsg;           // 에러 메시지

	@Builder
	protected ErrorResponse(final ErrorCode code) {
		this.resultMsg = code.getMessage();
		this.status = code.getStatus();
		this.divisionCode = code.getDivisionCode();
	}

	public static ErrorResponse of(final ErrorCode code) {
		return new ErrorResponse(code);
	}

}
