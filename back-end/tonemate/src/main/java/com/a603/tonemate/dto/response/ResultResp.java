package com.a603.tonemate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ApiModel(value = "음색/음역대 분석 검사 결과 리스트", description = "음색/음역대 검사 결과 목록의 정보들이 담김(검사 id, 검사 유형, 검사 일시)")
public class ResultResp {

//    @ApiModelProperty(value = "검사 결과 id", example = "1")
//    private Long resultId;
//
//    @ApiModelProperty(value = "검사 결과 유형(음색 or 음역대)")
//    private String type;
//
//    @ApiModelProperty(value = "검사 일시")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
//    private LocalDateTime time;

    @ApiModelProperty(value = "음역대 검사 결과 목록")
    private List<PitchResultResp> pitch;

    @ApiModelProperty(value = "음색 검사 결과 목록")
    private List<TimbreResultResp> timbre;

}
