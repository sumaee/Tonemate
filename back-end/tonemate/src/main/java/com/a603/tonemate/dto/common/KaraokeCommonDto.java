package com.a603.tonemate.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class KaraokeCommonDto {
    @ApiModelProperty(value = "TJ 노래방 번호", example = "1")
    private Integer tjNum;

    @ApiModelProperty(value = "가수 이름")
    private String singer;

    @ApiModelProperty(value = "노래 제목")
    private String title;
}
