package com.a603.tonemate.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String nickname;
    private String role;
    private String profile;

    @Builder
    public User(String username, String nickname, String role, String profile) {
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.profile = profile;
    }


    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfile(String profile) {
        this.profile = profile;
    }

}
