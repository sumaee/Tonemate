package com.a603.tonemate.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ApiModel(value = "마이페이지에서 사용할 사용자 정보", description = "사용자 정보들이 담김")
public class UserResp {
    @ApiModelProperty(value = "사용자 nickname")
    private String nickname;
    @ApiModelProperty(value = "사용자 프로필 이미지 url")
    private String profileImg;
}