package com.a603.tonemate.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(indexes = {
        @Index(name = "idx_tj_num", columnList = "tjNum"),
        @Index(name = "idx_title_no_space", columnList = "titleNoSpace"),
        @Index(name = "idx_singer_no_space", columnList = "singerNoSpace")})
public class Karaoke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long karaokeId;
    private String titleNoSpace;
    private String singerNoSpace;
    private Integer tjNum;
    private String title;
    private String singer;
}
