package com.troupe.backend.controller.member;

import com.troupe.backend.domain.member.Guestbook;
import com.troupe.backend.dto.guestbook.GuestbookContent;
import com.troupe.backend.dto.guestbook.GuestbookForm;
import com.troupe.backend.dto.guestbook.GuestbookResponse;
import com.troupe.backend.service.member.GuestbookService;
import com.troupe.backend.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@Api("방명록 REST API")
@RequestMapping("/guestbook")
@RestController
@RequiredArgsConstructor
public class GuestbookController {
    private final MemberService memberService;

    private final GuestbookService guestbookService;

    @Operation(summary = "호스트에게 모든 사람이 쓴 방명록 리스트 조회", description = "파라미터 : hostMemberNo(패스배리어블)")
    @GetMapping("/{hostMemberNo}/list")
    private ResponseEntity getGuestbookListOfHost(@PathVariable int hostMemberNo) {
        List<Guestbook> guestbookList = guestbookService.findGuestbookListOfHost(hostMemberNo);

        List<GuestbookResponse> responseList = new ArrayList<>();
        for (Guestbook guestbook : guestbookList) {
            responseList.add(new GuestbookResponse(guestbook));
        }

        if (responseList.isEmpty()) {
            return new ResponseEntity(responseList, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(responseList, HttpStatus.OK);
        }
    }

    @Operation(summary = "호스트에게 내가 쓴 방명록 조회", description = "파라미터 : hostMemberNo(패스배리어블), accessToken (리퀘스트헤더) ")
    @GetMapping("/{hostMemberNo}")
    private ResponseEntity getGuestbook(Principal principal, @PathVariable int hostMemberNo) {
        int visitorMemberNo = Integer.parseInt(principal.getName());

        Optional<Guestbook> found = guestbookService.findGuestBook(hostMemberNo, visitorMemberNo);

        if (found.isPresent()) {
            Guestbook guestbook = found.get();
            GuestbookResponse guestbookResponse = new GuestbookResponse(guestbook);

            return new ResponseEntity(guestbookResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity("작성한 방명록이 없습니다.\n", HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "방명록 작성", description = "파라미터 : hostMemberNo(패스배리어블), content(리퀘스트바디), accessToken (리퀘스트헤더) ")
    @PostMapping("/{hostMemberNo}")
    private ResponseEntity saveGuestbook(Principal principal, @RequestBody GuestbookContent guestbookContent, @PathVariable int hostMemberNo) {
        int visitorMemberNo = Integer.parseInt(principal.getName());
        String content = guestbookContent.getContent();

        GuestbookForm guestbookForm = GuestbookForm.builder()
                .hostMemberNo(hostMemberNo)
                .visitorMemberNo(visitorMemberNo)
                .content(content)
                .build();

        Guestbook guestbook = guestbookService.saveGuestbook(guestbookForm);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "방명록 수정", description = "파라미터 : hostMemberNo(패스배리어블), content(리퀘스트바디), accessToken (리퀘스트헤더) ")
    @PatchMapping("/{hostMemberNo}")
    private ResponseEntity updateGuestbook(Principal principal, @RequestBody GuestbookContent guestbookContent, @PathVariable int hostMemberNo) {
        int visitorMemberNo = Integer.parseInt(principal.getName());
        String content = guestbookContent.getContent();

        GuestbookForm guestbookForm = GuestbookForm.builder()
                .hostMemberNo(hostMemberNo)
                .visitorMemberNo(visitorMemberNo)
                .content(content)
                .build();

        guestbookService.updateGuestbook(guestbookForm);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "방명록 삭제", description = "파라미터 : hostMemberNo(패스배리어블), accessToken (리퀘스트헤더) ")
    @PatchMapping("/{hostMemberNo}/del")
    private ResponseEntity updateGuestbook(Principal principal, @PathVariable int hostMemberNo) {
        int visitorMemberNo = Integer.parseInt(principal.getName());

        int guestbookNo = guestbookService.findGuestBook(hostMemberNo, visitorMemberNo).get().getGuestbookNo();
        guestbookService.deleteGuestbook(guestbookNo);

        return ResponseEntity.noContent().build();
    }
}