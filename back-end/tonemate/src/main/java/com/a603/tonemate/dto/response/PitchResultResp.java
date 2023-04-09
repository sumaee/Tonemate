package com.a603.tonemate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@ApiModel(value = "음색 검사 결과 리스트", description = "음색 검사 결과 목록의 정보들이 담김(검사 id, 검사 일시, 유사도 높은 가수 목록(이름))")
public class PitchResultResp {

    @ApiModelProperty(value = "검사 결과 id", example = "1")
    private Long pitchId;

    @ApiModelProperty(value = "검사 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime time;

    @ApiModelProperty(value = "사용자 음역대의 최저음")
    private String lowOctave;

    @ApiModelProperty(value = "사용자 음역대의 최고음")
    private String highOctave;
}
