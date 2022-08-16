package com.troupe.backend.controller.security;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.service.security.JwtTokenProvider;
import com.troupe.backend.dto.security.RefreshForm;
import com.troupe.backend.dto.security.TokenResponse;
import com.troupe.backend.service.member.MemberService;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Api("리프레시 토큰 REST API")
@RequestMapping("/refresh")
@RestController
@RequiredArgsConstructor
public class RefreshController {
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberService memberService;

    @Operation(summary = "액세스 토큰 재발급 요청", description = "파라미터 : memberNo(리퀘스트바디), refreshToken(리퀘스트바디)")
    @PostMapping()
    public ResponseEntity refresh(@RequestBody RefreshForm refreshForm) {
        int memberNo = refreshForm.getMemberNo();
        String refreshToken = refreshForm.getRefreshToken();
        System.out.println(refreshToken);
        // 리프레시 토큰이 유효하지 않은 경우 재로그인 해야 함
        if (!jwtTokenProvider.validateRefreshToken(memberNo, refreshToken)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put(MyConstant.MESSAGE, "토큰이 만료되었습니다. 다시 로그인이 필요합니다.\n");
            return new ResponseEntity(resultMap, HttpStatus.UNAUTHORIZED);
        }

        // 리프레시 토큰이 유효하면 액세스 토큰 재발급
        else {
            Member member = memberService.findById(memberNo).get();
            String accessToken = jwtTokenProvider.recreateAccessToken(String.valueOf(memberNo), member.getRoles());

            TokenResponse tokenResponse = TokenResponse.builder().memberNo(memberNo).accessToken(accessToken).refreshToken(refreshToken).build();
            tokenResponse.setAccessToken(accessToken);

            return new ResponseEntity(tokenResponse, HttpStatus.OK);
        }
    }
}
