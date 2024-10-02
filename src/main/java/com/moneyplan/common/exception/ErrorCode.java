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
    INVALID_AMOUNT_EXCEPTION(HttpStatus.BAD_REQUEST, "예산 금액은 0 이상이어야 합니다."),

    /**
     * 401 - Unauthorized
     */
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "계정명 또는 비밀번호가 틀렸습니다."),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효기간이 만료된 토큰입니다."),
    MISSING_TOKEN_IN_COOKIE(HttpStatus.UNAUTHORIZED, "쿠키에 토큰이 존재하지 않습니다."),
    MISSING_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증 정보가 존재하지 않습니다."),

    /**
     * 404 - Not Found
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    EXPENSE_NOT_FOUND(HttpStatus.NOT_FOUND, "지출을 찾을 수 없습니다."),
    JWT_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),

    /**
     * 403 - Forbidden
     */
    ACCOUNT_CONFLICT(HttpStatus.CONFLICT, "이미 사용중인 계정입니다."),
    FORBIDDEN_ACCESS(HttpStatus.CONFLICT, "접근 권한이 없습니다."),

    /**
     * 500 - Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}