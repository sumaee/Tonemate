package com.a603.tonemate.dto.common;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class KaraokeTopDto extends KaraokeCommonDto {
    @ApiModelProperty(value = "음악 순위", example = "1")
    private Long karaokeTopId;
    @ApiModelProperty(value = "애창곡 존재 여부")
    private Boolean isLike;

    @QueryProjection
    public KaraokeTopDto(Long karaokeTopId, Integer tjNum, String singer, String title, Boolean isLike) {
        super(tjNum, singer, title);
        this.karaokeTopId = karaokeTopId;
        this.isLike = isLike;
    }
}
