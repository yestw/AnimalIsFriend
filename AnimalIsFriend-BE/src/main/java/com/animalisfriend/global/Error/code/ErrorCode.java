package com.animalisfriend.global.Error.code;

import lombok.Getter;

@Getter
public enum ErrorCode {

	/**
	 * ******************************* Global Error CodeList ***************************************
	 * HTTP Status Code
	 * 400 : Bad Request
	 * 401 : Unauthorized
	 * 403 : Forbidden
	 * 404 : Not Found
	 * 500 : Internal Server Error
	 * *********************************************************************************************
	 */
	MISSING_INPUT_VALUE(400, "G013", "입력된 인자의 수가 부족합니다."),
	METHOD_NOT_ALLOWED(405, "G014", "지원되지 않는 메서드입니다."),
	HANDLE_ACCESS_DENIED(403, "G015", "접근 권한이 없습니다."),


	// 잘못된 서버 요청
	BAD_REQUEST_ERROR(400, "G001", "Bad Request Exception"),
	// @RequestBody 데이터 미 존재
	REQUEST_BODY_MISSING_ERROR(400, "G002", "Required request body is missing"),
	// 유효하지 않은 타입
	INVALID_TYPE_VALUE(400, "G003", " Invalid Type Value"),
	// Request Parameter 로 데이터가 전달되지 않을 경우
	MISSING_REQUEST_PARAMETER_ERROR(400, "G004", "Missing Servlet RequestParameter Exception"),
	// 입력/출력 값이 유효하지 않음
	IO_ERROR(400, "G005", "I/O Exception"),
	// com.google.gson JSON 파싱 실패
	JSON_PARSE_ERROR(400, "G006", "JsonParseException"),
	// com.fasterxml.jackson.core Processing Error
	JACKSON_PROCESS_ERROR(400, "G007", "com.fasterxml.jackson.core Exception"),

	// 권한이 없음
	FORBIDDEN_ERROR(403, "G008", "Forbidden Exception"),

	// 서버로 요청한 리소스가 존재하지 않음
	NOT_FOUND_ERROR(404, "G009", "Not Found Exception"),
	// NULL Point Exception 발생
	NULL_POINT_ERROR(404, "G010", "Null Point Exception"),
	// @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
	NOT_VALID_ERROR(404, "G011", "handle Validation Exception"),
	// @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
	NOT_VALID_HEADER_ERROR(404, "G012", "Header에 데이터가 존재하지 않는 경우 "),

	// 서버가 처리 할 방법을 모르는 경우 발생
	INTERNAL_SERVER_ERROR(500, "G999", "Internal Server Error Exception"),


	/**
	 * ******************************* Custom Error CodeList ***************************************
	 * HTTP Status Code
	 * *********************************************************************************************
	 */

	//user
	USER_NOT_FOUND(404, "U001", "사용자가 존재하지 않습니다."),
	USER_DUPLICATION(500,"U002", "사용자가 이미 존재합니다."),

	//pet
	PET_NOT_FOUND(404, "P001", "반려동물이 존재하지 않습니다."),
	PET_NOT_MATCH(500, "P002", "사용자가 등록한 반려동물이 아닙니다."),

	//chatroom
	CHATROOM_ALREADY_EXIST(500, "R001", "이미 채팅방이 존재합니다."),
	CANNOT_CREATE_CHATROOM(500, "R002", "자기 자신을 대상으로 한 채팅방은 만들 수 없습니다."),

	//token
	TOKEN_EXPIRED(401, "T001", "만료된 토큰입니다."),
	TOKEN_INVALID(401, "T002", "유효하지 않은 토큰입니다."),

	//oauth2
	PROVIDER_NOT_FOUND(404, "O001", "OAUTH2를 제공하는 플랫폼이 존재하지 않습니다."),
	;

	private final int status;
	private final String divisionCode;
	private final String message;

	ErrorCode(final int status, final String divisionCode, final String message) {
		this.status = status;
		this.divisionCode = divisionCode;
		this.message = message;
	}
}
