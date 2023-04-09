package com.a603.tonemate.api.service;

import com.a603.tonemate.db.entity.Karaoke;
import com.a603.tonemate.db.entity.LikeSong;
import com.a603.tonemate.dto.common.KaraokeCommonDto;
import com.a603.tonemate.dto.response.KaraokeResp;
import org.springframework.data.domain.Pageable;

public interface LikeSongService {
    KaraokeResp getLikeList(Long userId, Pageable pageable);

    void addLikeSong(Long userId, Integer tjNum);

    void deleteLikeSong(Long userId, Integer tjNum);

    default KaraokeCommonDto toDto(LikeSong likeSong) {
        Karaoke karaoke = likeSong.getKaraoke();
        return KaraokeCommonDto.builder()
                .tjNum(karaoke.getTjNum())
                .singer(karaoke.getSinger())
                .title(karaoke.getTitle())
                .build();
    }
}
