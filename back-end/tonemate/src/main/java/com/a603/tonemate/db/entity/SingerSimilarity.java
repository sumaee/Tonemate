package com.a603.tonemate.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "singer_similarity")
public class SingerSimilarity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SingerSimilarityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timbre_id")
    private TimbreAnalysis timbreAnalysis;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "singer_id")
    private Singer singer;
    private Float similarityPercent;

    public SingerSimilarity(TimbreAnalysis timbreAnalysis, Singer singer, Float similarityPercent) {
        this.timbreAnalysis = timbreAnalysis;
        this.singer = singer;
        this.similarityPercent = similarityPercent;
    }
}
