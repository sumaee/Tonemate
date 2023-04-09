package com.a603.tonemate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class SongResp {
    private String title;
    private Float mfccMean;
    private Float stftMean;
    private Float zcrMean;
    private Float spcMean;
    private Float sprMean;
    private Float rmsMean;
    private Float mfccVar;
    private Float stftVar;
    private Float zcrVar;
    private Float spcVar;
    private Float sprVar;
    private Float rmsVar;
}
