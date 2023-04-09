package com.a603.tonemate.exception;

import java.util.NoSuchElementException;

public class AnalysisPitchByGenreException extends RuntimeException {
    public AnalysisPitchByGenreException(String message) {
        super(message);
    }

    public static AnalysisPitchByGenreException fromException(Exception e) {
        if (e instanceof NoSuchElementException) {
            return new AnalysisPitchByGenreException("요청한 유저는 해당 정보에 접근할 권한이 없습니다. 로그인 유저의 ID와 Path에서 ID가 일치해야합니다.");
        } else if(e instanceof NumberFormatException){
        	return new AnalysisPitchByGenreException("[]를 처리하는 경우 발생할 수 있는 에러입니다.");
        }else {
            return new AnalysisPitchByGenreException("예상하지 못한 에러입니다. : " + e.getMessage());
        }
    }
}
