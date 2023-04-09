package com.a603.tonemate.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeSongId;
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "karaoke_id")
    private Karaoke karaoke;

    @Builder
    public LikeSong(Long userId, Karaoke karaoke) {
        this.userId = userId;
        this.karaoke = karaoke;
    }
}
