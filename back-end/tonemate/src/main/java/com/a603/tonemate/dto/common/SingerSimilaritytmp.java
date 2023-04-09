package com.a603.tonemate.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingerSimilaritytmp implements Comparable<SingerSimilaritytmp> {

    // 가수 이름
    private String name;
    // 유사도
    private float similarityPercent;

    // 유사도 높은 순으로 정렬(내림차순)
    @Override
    public int compareTo(SingerSimilaritytmp o) {
        if (o.similarityPercent > this.similarityPercent) {
            return 1;
        } else if (o.similarityPercent < this.similarityPercent) {
            return -1;
        } else {
            return 0;
        }
    }
}
