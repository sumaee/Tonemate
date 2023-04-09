package com.a603.tonemate.exception;

public class NoFileException extends RuntimeException {
    public NoFileException(String message) {
        super(message);
    }

    public NoFileException() {
        super("파일이 없음");
    }
}
