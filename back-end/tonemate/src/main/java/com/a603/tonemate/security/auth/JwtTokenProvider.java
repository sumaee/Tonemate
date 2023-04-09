package com.a603.tonemate.security.auth;

import com.a603.tonemate.db.entity.User;
import com.a603.tonemate.db.repository.UserRepository;
import com.a603.tonemate.dto.request.TokenReq;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * JWT 토큰 생성, 토큰 복호화 및 정보 추출, 토큰 유효성 검증의 기능이 구현된 클래스
 */
@Component
public class JwtTokenProvider {
    private final UserRepository userRepository;
    private final Key key;

    public JwtTokenProvider(UserRepository userRepository, @Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
    }

    /**
     * // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
     */
    public TokenInfo generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        User user = userRepository.findByNickname(authentication.getName()).orElseThrow();
        long now = new Date().getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + JwtProperties.ACCESS_TOKEN_TIME);
        String accessToken = Jwts.builder()
                .claim("id", user.getUserId())
                .claim("name", user.getUsername())
                .claim(JwtProperties.AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + JwtProperties.REFRESH_TOKEN_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new TokenInfo(accessToken, refreshToken, user.getUserId());
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(JwtProperties.AUTHORITIES_KEY) == null) {
            throw new MalformedJwtException("손상된 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(JwtProperties.AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        User user = userRepository.findById(Long.parseLong(claims.get("id").toString())).orElseThrow(IllegalArgumentException::new);
        UserDetailsCustom principal = new UserDetailsCustom(user);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return true;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getId(String token) {
        return Long.parseLong(parseClaims(token).get("id").toString());
    }

//    public String parseToken(String token) {
//        if (token.startsWith(JwtProperties.TOKEN_PREFIX)) {
//            token = token.substring(JwtProperties.TOKEN_PREFIX.length());
//        }
//        return token;
//    }

    public TokenReq getToken(HttpServletRequest request) {
        TokenReq tokenReq = new TokenReq();
        for (Cookie cookie : request.getCookies()) {
            String name = cookie.getName();
            if (name.equals("accessToken")) {
                tokenReq.setAccessToken(cookie.getValue());
            } else if (name.equals("refreshToken")) {
                tokenReq.setRefreshToken(cookie.getValue());
            }
        }
        return tokenReq;
    }

    public String getAccessToken(ServletRequest request) {
        if (((HttpServletRequest) request).getCookies() != null) {
            for (Cookie cookie : ((HttpServletRequest) request).getCookies()) {
                String name = cookie.getName();
                if (name.equals("accessToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
