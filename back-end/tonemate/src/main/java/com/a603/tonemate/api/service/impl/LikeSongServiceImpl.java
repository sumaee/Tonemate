package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.LikeSongService;
import com.a603.tonemate.db.entity.LikeSong;
import com.a603.tonemate.db.repository.KaraokeRepository;
import com.a603.tonemate.db.repository.LikeSongRepository;
import com.a603.tonemate.dto.common.KaraokeCommonDto;
import com.a603.tonemate.dto.response.KaraokeResp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeSongServiceImpl implements LikeSongService {

    private final LikeSongRepository likeSongRepository;
    private final KaraokeRepository karaokeRepository;

    @Override
    public KaraokeResp getLikeList(Long userId, Pageable pageable) {
        Page<KaraokeCommonDto> likeSongs = likeSongRepository.findByUserId(userId, pageable).map(this::toDto);
        return KaraokeResp.builder()
                .songs(likeSongs.getContent())
                .pageSize(likeSongs.getPageable().getPageSize())
                .totalPageNumber(likeSongs.getTotalPages())
                .totalCount(likeSongs.getTotalElements())
                .build();
    }

    @Override
    public void addLikeSong(Long userId, Integer tjNum) {
        likeSongRepository.save(LikeSong.builder()
                .userId(userId)
                .karaoke(karaokeRepository.findByTjNum(tjNum).orElseThrow())
                .build()
        );
    }

    @Override
    public void deleteLikeSong(Long userId, Integer tjNum) {
        likeSongRepository.delete(userId, tjNum);
    }
}
