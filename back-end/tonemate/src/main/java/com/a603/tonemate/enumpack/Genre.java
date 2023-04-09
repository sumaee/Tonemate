package com.a603.tonemate.enumpack;

public enum Genre {
	BALLADE("발라드"),
	ROCK("락"),
	UNKNOW("UNKNOW");
	final private String code;
	private Genre(String code) { 
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public static Genre fromCode(String code) {
        for (Genre genre : Genre.values()) {
            if (genre.getCode().equals(code)) {
                return genre;
            }
        }
        return Genre.UNKNOW;
    }
    
    public static boolean isValid(String code) {
        for (Genre g : Genre.values()) {
            if (g.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
