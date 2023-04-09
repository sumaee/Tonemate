package com.a603.tonemate.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class PitchResult {
    private int pitch;
    private boolean isSuccess;
}
