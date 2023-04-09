package com.a603.tonemate.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TimbreAnalysis extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timbre_id")
    private Long timbreId;
    private Long userId;
    private float mfccMean;
    private float stftMean;
    private float zcrMean;
    private float spcMean;
    private float sprMean;
    private float rmsMean;
    private float mfccVar;
    private float stftVar;
    private float zcrVar;
    private float spcVar;
    private float sprVar;
    private float rmsVar;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "timbreAnalysis")
    private List<SingerSimilarity> singerSimilarities = new ArrayList<>();

    @Builder
    public TimbreAnalysis(Long userId, float mfccMean, float stftMean, float zcrMean, float spcMean, float sprMean, float rmsMean, float mfccVar, float stftVar, float zcrVar, float spcVar, float sprVar, float rmsVar) {
        this.userId = userId;
        this.mfccMean = mfccMean;
        this.stftMean = stftMean;
        this.zcrMean = zcrMean;
        this.spcMean = spcMean;
        this.sprMean = sprMean;
        this.rmsMean = rmsMean;
        this.mfccVar = mfccVar;
        this.stftVar = stftVar;
        this.zcrVar = zcrVar;
        this.spcVar = spcVar;
        this.sprVar = sprVar;
        this.rmsVar = rmsVar;
    }
}
