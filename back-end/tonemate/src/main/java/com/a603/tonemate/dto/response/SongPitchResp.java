package com.a603.tonemate.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SongPitchResp extends SongResp{

    @ApiModelProperty(value = "사용자 음역대의 최저음")
    private String lowOctave;

    @ApiModelProperty(value = "사용자 음역대의 최고음")
    private String highOctave;

    @ApiModelProperty(value = "가수 이름")
    private String singer;

}
