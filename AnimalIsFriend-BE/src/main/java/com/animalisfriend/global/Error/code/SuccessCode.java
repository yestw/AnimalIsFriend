package com.animalisfriend.global.Error.code;

import lombok.Getter;

@Getter
public enum SuccessCode {

	/**
	 * ******************************* Success CodeList ***************************************
	 */
	// 조회 성공 코드 (HTTP Response: 200 OK)
	SELECT_SUCCESS(200, "200", "SELECT SUCCESS"),
	// 삭제 성공 코드 (HTTP Response: 200 OK)
	DELETE_SUCCESS(200, "200", "DELETE SUCCESS"),
	// 삽입 성공 코드 (HTTP Response: 201 Created)
	INSERT_SUCCESS(201, "201", "INSERT SUCCESS"),
	// 수정 성공 코드 (HTTP Response: 201 Created)
	UPDATE_SUCCESS(204, "204", "UPDATE SUCCESS"),

	//user
	SIGNUP_SUCCESS(200, "US001", "회원가입이 완료되었습니다."),

	//chatROOM
	CHATROOM_CREATE_SUCCESS(200, "R001", "채팅방이 개설되었습니다."),

	//pet
	PET_REGISTER_SUCCESS(200, "PS001", "펫 등록이 완료되었습니다."),

	; // End

	/**
	 * ******************************* Success Code Constructor ***************************************
	 */
	// 성공 코드의 '코드 상태'를 반환한다.
	private final int status;

	// 성공 코드의 '코드 값'을 반환한다.
	private final String code;

	// 성공 코드의 '코드 메시지'를 반환한다.s
	private final String message;

	// 생성자 구성
	SuccessCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
