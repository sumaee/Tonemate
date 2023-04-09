package com.a603.tonemate.dto.response;

import com.a603.tonemate.dto.common.KaraokeCommonDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class KaraokeResp {

    @ApiModelProperty(value = "노래 정보들")
    private List<? extends KaraokeCommonDto> songs;

    @ApiModelProperty(value = "한 페이지에서 나타낼수 있는 노래의 수", example = "1")
    private Integer pageSize;
    @ApiModelProperty(value = "전체 페이지 번호", example = "1")
    private Integer totalPageNumber;

    @ApiModelProperty(value = "찾은 노래 개수", example = "1")
    private Long totalCount;

}
