package com.a603.tonemate.api.service;

import com.a603.tonemate.security.auth.TokenInfo;

import javax.servlet.http.HttpServletRequest;

public interface JwtService {
    TokenInfo reissue(HttpServletRequest request);

}
