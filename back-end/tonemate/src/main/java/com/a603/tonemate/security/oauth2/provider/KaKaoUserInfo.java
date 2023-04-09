package com.a603.tonemate.security.oauth2.provider;

import java.util.Map;

public class KaKaoUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;
    private final Map<String, String> properties;

    public KaKaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        properties = (Map<String, String>) attributes.get("properties");
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProfile() {
        return properties.get("profile_image");
    }
}
