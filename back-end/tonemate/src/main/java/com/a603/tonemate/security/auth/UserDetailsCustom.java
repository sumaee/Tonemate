package com.a603.tonemate.security.auth;

import com.a603.tonemate.db.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserDetailsCustom implements OAuth2User {
    private final User user;
    private Map<String, Object> attributes;

    public UserDetailsCustom(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public UserDetailsCustom(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getNickname();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(user.getRole()));
        return list;
    }

}
