package com.a603.tonemate.api.controller;

import com.a603.tonemate.api.service.LikeSongService;
import com.a603.tonemate.dto.response.KaraokeResp;
import com.a603.tonemate.security.auth.JwtProperties;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Api(tags = {"애창곡 관련 controller"})
public class LikeSongController {
    private static final String SUCCESS = "success";
    private final JwtTokenProvider jwtTokenProvider;

    private final LikeSongService likeSongService;

    @ApiOperation(value = "사용자가 찜한 노래 목록 불러오기", notes = "노래들의 기본 정보들 보여줌")
    @GetMapping("/likes")
    public ResponseEntity<KaraokeResp> likeSongs(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken, @PageableDefault(size = 10) Pageable pageable) {
        Long userId = jwtTokenProvider.getId(accessToken);
        return new ResponseEntity<>(likeSongService.getLikeList(userId, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "노래 찜하기", notes = "노래 찜하기")
    @PostMapping("/likes/{tjNum}")
    public ResponseEntity<?> addLikeSong(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken, @PathVariable Integer tjNum) {
        Long userId = jwtTokenProvider.getId(accessToken);
        System.out.println(tjNum);
        likeSongService.addLikeSong(userId, tjNum);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "노래 찜한거 삭제하기", notes = "노래 찜 삭제하기")
    @DeleteMapping("/likes/{tjNum}")
    public ResponseEntity<?> deleteLikeSong(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken, @PathVariable Integer tjNum) {
        Long userId = jwtTokenProvider.getId(accessToken);
        likeSongService.deleteLikeSong(userId, tjNum);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }


}
