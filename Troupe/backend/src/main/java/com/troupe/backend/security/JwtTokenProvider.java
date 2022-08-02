package com.troupe.backend.security;

import com.troupe.backend.util.MyConstant;
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
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final RefreshTokenService refreshTokenService;

    /**
     * 암호화 키
     */
    private String accessSecretKey = "asdlfksndfklsdnioasdgiohsgiasegniasnldgnaslgnasfafls";
    private String refreshSecretKey = "wentinasgklnsklgnaklsgniwehlkweaitnslngklsnaasdfdksdf";

    /**
     * 토큰 유효 시간
     */
    private long accessTokenValidTime = 30 * 60 * 1000L; // 30분
    private long refreshTokenValidTime = 14 * 24 * 60 * 60 * 1000L; // 14일

    private final UserDetailsService userDetailsService;

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 객체 초기화, secretKey를 Base64로 인코딩
     */
    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
    }

    /**
     * JWT 토큰 생성
     */
    public TokenResponse createToken(String userPK, List<String> roles) {
        int memberNo = Integer.parseInt(userPK);

        // Claims : JWT payload 에 저장되는 정보 단위
        Claims claims = Jwts.claims().setSubject(userPK); // 유저 PK 저장
        claims.put("roles", roles); // 유저 역할 저장
        Date now = new Date();

        // 액세스 토큰 발급
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보 세팅
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // SHA-256 알고리즘 사용, 암호화 키 세팅
                .compact();

        // 리프레시 토큰 발급
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();

        // 리프레시 토큰을 DB 에 저장
        refreshTokenService.saveRefreshToken(memberNo, refreshToken);

        return TokenResponse.builder().memberNo(memberNo).accessToken(accessToken).refreshToken(refreshToken).build();
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
        return Jwts.parserBuilder().setSigningKey(accessSecretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * requestHeader 에서 액세스 토큰 가져오기
     */
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader(MyConstant.ACCESS_TOKEN);
    }

    /**
     * 액세스 토큰의 유효성 검증
     */
    public boolean validateAccessToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(accessSecretKey).build().parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 리프레시 토큰의 유효성 검증
     */
    public boolean validateRefreshToken(int memberNo, String refreshToken) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(refreshSecretKey).build().parseClaimsJws(refreshToken);

        // 리프레시 토큰의 유효성, 만료 여부 검증
        if (!claims.getBody().getExpiration().before(new Date())) {
            // DB에 리프레시 토큰 존재 여부 확인
            Optional<RefreshToken> found = refreshTokenRepository.findByRefreshToken(refreshToken);
            if (found.isPresent()) {
                int memberNoFromDB = found.get().getMemberNo();

                // 리프레시 토큰이 할당된 유저 번호와 요청을 보낸 유저 번호가 같은지 검증
                if (memberNoFromDB == memberNo) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 액세스 토큰 재발급
     */
    public String recreateAccessToken(String userPK, List<String> roles) {
        // Claims : JWT payload 에 저장되는 정보 단위
        Claims claims = Jwts.claims().setSubject(userPK); // 유저 PK 저장
        claims.put("roles", roles); // 유저 역할 저장
        Date now = new Date();

        // 액세스 토큰 재발급
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보 세팅
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // SHA-256 알고리즘 사용, 암호화 키 세팅
                .compact();

        return accessToken;
    }

    /**
     * 리프레시 토큰 재발급
     */
    public String recreateRefreshToken(String userPK, List<String> roles) {
        int memberNo = Integer.parseInt(userPK);

        // Claims : JWT payload 에 저장되는 정보 단위
        Claims claims = Jwts.claims().setSubject(userPK); // 유저 PK 저장
        claims.put("roles", roles); // 유저 역할 저장
        Date now = new Date();

        // 리프레시 토큰 재발급
        String refreshToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보 세팅
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)  // SHA-256 알고리즘 사용, 암호화 키 세팅
                .compact();

        // 리프레시 토큰을 DB 에 저장
        refreshTokenService.saveRefreshToken(memberNo, refreshToken);

        return refreshToken;
    }

}
