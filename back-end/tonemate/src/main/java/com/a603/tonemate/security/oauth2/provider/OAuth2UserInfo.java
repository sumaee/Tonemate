package com.a603.tonemate.security.oauth2.provider;

public interface OAuth2UserInfo {
    String getProviderId();

    String getProvider();

    String getProfile();
}
