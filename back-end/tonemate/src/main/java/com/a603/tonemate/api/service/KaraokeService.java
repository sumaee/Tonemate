package com.a603.tonemate.api.service;

import com.a603.tonemate.dto.request.SearchSongReq;
import com.a603.tonemate.dto.response.KaraokeResp;
import org.springframework.data.domain.Pageable;

public interface KaraokeService {
    //노래방 Top 100 출력
    KaraokeResp selectTopSong(Pageable pageable, Long userId);

    //등록되 있는 모든 노래 출력
    KaraokeResp selectAllSong(Pageable pageable, Long userId);

    //노래 검색
    KaraokeResp searchSong(SearchSongReq searchSongReq, Pageable pageable, Long userId);

}
