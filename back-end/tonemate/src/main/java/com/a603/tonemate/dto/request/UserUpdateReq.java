package com.a603.tonemate.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "사용자 프로필 수정 시 필요한 정보")
public class UserUpdateReq {
    @ApiModelProperty(value = "변경할 닉네임")
    private String nickname;
}
