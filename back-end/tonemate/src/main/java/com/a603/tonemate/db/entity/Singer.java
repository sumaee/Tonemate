package com.a603.tonemate.db.entity;

import com.a603.tonemate.enumpack.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(name = "idx_genre", columnList = "genre")})//index
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long singerId;
    private String name;
    @Column(nullable = true)
    private Boolean gender;
    private Date birthYear;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(100) default 'UNKNOW'")
    private Genre genre;
    @OneToMany(mappedBy = "singer")
    @BatchSize(size = 5)
    private List<Song> songs;
}
