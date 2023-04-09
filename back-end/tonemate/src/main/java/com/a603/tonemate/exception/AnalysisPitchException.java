package com.a603.tonemate.exception;

import java.util.NoSuchElementException;

public class AnalysisPitchException extends RuntimeException {
    public AnalysisPitchException(String message) {
        super(message);
    }

    public static AnalysisPitchException fromException(Exception e) {
        if (e instanceof NoFileException) {
            return new AnalysisPitchException("파일을 찾지 못했습니다.");
        } else if(e instanceof UnsupportedPitchFileException){
        	return new AnalysisPitchException("음역대 파일이 잘못되었습니다.");
        }else {
            return new AnalysisPitchException("예상하지 못한 에러입니다. : " + e.getMessage());
        }
    }
}
