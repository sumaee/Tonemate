package com.a603.tonemate.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class TimbreResultResp {
    @ApiModelProperty(value = "검사 결과 id", example = "1")
    private Long timbreId;
    @ApiModelProperty(value = "검사 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime time;
    @ApiModelProperty(value = "유사도 높은 가수 목록")
    private List<String> singer;
}
