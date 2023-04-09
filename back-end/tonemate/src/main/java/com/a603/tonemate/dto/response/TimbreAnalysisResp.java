package com.a603.tonemate.dto.response;

import com.a603.tonemate.dto.common.SingerDetail;
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
@ApiModel(value = "음색 분석 검사 결과", description = "사용자가 보낸 wav 파일에 대한 음색 분석 결과 정보들이 담김")
public class TimbreAnalysisResp {
    @ApiModelProperty(value = "음색 분석 결과 id", example = "1")
    private Long timbreId;

    @ApiModelProperty(value = "음악 특성 값들", example = "1.0")
    private float mfccMean;
    private float stftMean;
    private float zcrMean;
    private float spcMean;
    private float sprMean;
    private float rmsMean;
    private float mfccVar;
    private float stftVar;
    private float zcrVar;
    private float spcVar;
    private float sprVar;
    private float rmsVar;

    @ApiModelProperty(value = "음색 분석 검사 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime time;

    @ApiModelProperty(value = "유사도가 높은 가수 정보 제공")
    private List<SingerDetail> singerDetails;
}
