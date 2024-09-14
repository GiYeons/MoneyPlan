package com.moneyplan.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 400 - Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다. 입력값을 확인하고 다시 시도해주세요."),
    ARGUMENT_TYPE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "입력 값이 예상된 형식이나 유형과 일치하지 않습니다."),
    METHOD_ARGUMENT_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "입력된 값이 유효하지 않습니다. 각 파라미터의 조건을 확인해 주세요."),
    INVALID_FORMAT_EXCEPTION(HttpStatus.BAD_REQUEST, "요청된 데이터의 형식이 잘못되었습니다. 유효한 JSON 형식을 사용해 주세요."),
    MISSING_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, "필수 요청 값이 누락되었거나 잘못되었습니다."),

    /**
     * 500 - Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}