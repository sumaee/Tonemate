package com.a603.tonemate.exception;

public class ValidateTokenException extends RuntimeException {
    public ValidateTokenException(String message) {
        super(message);
    }

    public ValidateTokenException() {
        super("Refresh token 정보가 유효하지 않음");
    }
}
