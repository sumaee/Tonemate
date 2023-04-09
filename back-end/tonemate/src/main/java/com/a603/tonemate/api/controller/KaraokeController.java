package com.a603.tonemate.api.controller;

import com.a603.tonemate.api.service.KaraokeService;
import com.a603.tonemate.dto.request.SearchSongReq;
import com.a603.tonemate.dto.response.KaraokeResp;
import com.a603.tonemate.security.auth.JwtProperties;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import com.a603.tonemate.util.annotation.QueryStringArgResolver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/karaoke")
@Api(tags = {"노래방 관련 API"})
public class KaraokeController {

    private final KaraokeService karaokeService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "노래방 Top 100 출력", notes = "tj top 100 출력")
    @GetMapping("/top")
    public ResponseEntity<KaraokeResp> selectTopSong(@PageableDefault(size = 10) Pageable pageable,
                                                     @CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken) {
        Long userId = jwtTokenProvider.getId(accessToken);
        return new ResponseEntity<>(karaokeService.selectTopSong(pageable, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "노래방책 번호 출력", notes = "tj 노래 출력")
    @GetMapping("/songs")
    public ResponseEntity<KaraokeResp> selectAllSongs(@PageableDefault(size = 10) Pageable pageable,
                                                      @CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken) {
        Long userId = jwtTokenProvider.getId(accessToken);
        return new ResponseEntity<>(karaokeService.selectAllSong(pageable, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "노래 검색", notes = "tj 번호 출력")
    @GetMapping("/search")
    public ResponseEntity<KaraokeResp> searchSong(@QueryStringArgResolver SearchSongReq searchSongReq,
                                                  @PageableDefault(size = 10) Pageable pageable,
                                                  @CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken) {
        Long userId = jwtTokenProvider.getId(accessToken);
        return new ResponseEntity<>(karaokeService.searchSong(searchSongReq, pageable, userId), HttpStatus.OK);
    }


}
