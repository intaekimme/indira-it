package com.troupe.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    /**
     * 암호화 키
     */
    private String secretKey = "asdlfksndfklsdnioasdgiohsgiasegniasnldgnaslgksgnls";

    /**
     * 토큰 유효 시간
     */
    private long tokenValidTime = 30 * 60 * 1000L; // 30분

    private final UserDetailsService userDetailsService;

    /**
     * 객체 초기화, secretKey를 Base64로 인코딩
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * JWT 토큰 생성
     */
    public String createToken(String userPk, List<String> roles) {
        // Claims : JWT payload 에 저장되는 정보 단위
        Claims claims = Jwts.claims().setSubject(userPk); // 유저 PK 저장
        claims.put("roles", roles); // 유저 역할 저장
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보 세팅
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)  // SHA-256 알고리즘 사용, 암호화 키 세팅
                .compact();
    }

    /**
     * JWT 토큰에서 인증 정보 조회
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰에서 회원 정보 추출
     */
    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * requestHeader에서 token 가져오기 ("Authorization" : "TOKEN값")
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * 토큰의 유효성, 만료 여부 확인
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
