package com.a603.tonemate.dto.common;

import com.a603.tonemate.dto.response.SongResp;
import lombok.Getter;

import java.util.List;

@Getter
public class SingerDetail {
    // 가수 이름
    private String name;
    // 유사도
    private Float similarityPercent;
    private List<SongResp> songs;

    public SingerDetail(String name, Float similarityPercent, List<SongResp> songs) {
        this.name = name;
        this.similarityPercent = similarityPercent;
        this.songs = songs;
    }
}
