package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.UserService;
import com.a603.tonemate.db.entity.User;
import com.a603.tonemate.db.repository.UserRepository;
import com.a603.tonemate.dto.request.UserUpdateReq;
import com.a603.tonemate.dto.response.UserResp;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import com.a603.tonemate.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileUtil fileUtil;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    @Override
    public void updateUser(String token, MultipartFile multipartFile, UserUpdateReq param) throws IOException {
        User user = userRepository.findById(jwtTokenProvider.getId(token)).orElseThrow();
        if (multipartFile != null) {
            String url = fileUtil.upload(multipartFile, "profile");
            user.updateProfile(url);
        }
        if (StringUtils.hasText(param.getNickname())) {
            user.updateNickName(param.getNickname());
        }
    }

    @Override
    public UserResp selectOneUser(Long userId) {
        return toDto(userRepository.findById(userId).orElseThrow());
    }

    @Override
    public boolean checkNickname(String nickname) {
        System.out.println(userRepository.existsByNickname(nickname));
        return userRepository.existsByNickname(nickname);
    }


}
