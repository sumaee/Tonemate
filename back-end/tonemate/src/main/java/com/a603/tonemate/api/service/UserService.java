package com.a603.tonemate.api.service;

import com.a603.tonemate.db.entity.User;
import com.a603.tonemate.dto.request.UserUpdateReq;
import com.a603.tonemate.dto.response.UserResp;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    //유저 정보 수정
    void updateUser(String token, MultipartFile multipartFile, UserUpdateReq param) throws IOException;

    UserResp selectOneUser(Long userId);

    boolean checkNickname(String nickname);


    default UserResp toDto(User user) {
        return UserResp.builder()
                .profileImg(user.getProfile())
                .nickname(user.getNickname())
                .build();

    }

}
