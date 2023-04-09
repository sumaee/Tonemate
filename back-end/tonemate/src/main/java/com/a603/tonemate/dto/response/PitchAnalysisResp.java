package com.a603.tonemate.dto.response;

import com.a603.tonemate.db.entity.Song;
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
@ApiModel(value = "음역대 분석 검사 결과", description = "사용자가 보낸 wav 파일에 대한 음역대 분석 결과 정보들이 담김")
public class PitchAnalysisResp {
    @ApiModelProperty(value = "음역대 분석 결과 id", example = "1")
    private Long pitchId;

    @ApiModelProperty(value = "사용자 음역대의 최저음")
    private String lowOctave;

    @ApiModelProperty(value = "사용자 음역대의 최고음")
    private String highOctave;

    @ApiModelProperty(value = "음역대 분석 검사 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime time;

    @ApiModelProperty(value = "잘 부를 수 있는 노래")
    private List<SongPitchResp> possibleSong;

    @ApiModelProperty(value = "적당히 부를 수 있는 노래")
    private List<SongPitchResp> normalSong;

    @ApiModelProperty(value = "못 부르는 노래")
    private List<SongPitchResp> impossibleSong;


}
