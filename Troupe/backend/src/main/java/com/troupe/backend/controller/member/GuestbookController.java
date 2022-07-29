package com.troupe.backend.controller.member;

import com.troupe.backend.domain.member.Guestbook;
import com.troupe.backend.dto.guestbook.GuestbookForm;
import com.troupe.backend.dto.guestbook.GuestbookResponse;
import com.troupe.backend.service.member.GuestbookService;
import com.troupe.backend.service.member.MemberService;
import com.troupe.backend.util.MyConstant;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.troupe.backend.util.MyUtil.getMemberNoFromRequestHeader;

@CrossOrigin
@Api("방명록 REST API")
@RequestMapping("/guestbook")
@RestController
public class GuestbookController {
    private MemberService memberService;

    private GuestbookService guestbookService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    public void setGuestbookService(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    /**
     * host의 모든 방명록 조회
     */
    @GetMapping("/{hostMemberNo}/list")
    private ResponseEntity getGuestbookListOfHost(@PathVariable int hostMemberNo) {
        List<Guestbook> guestbookList = guestbookService.findGuestbookListOfHost(hostMemberNo);
        System.out.println(guestbookList.toString());

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

    /**
     * host에게 내가 쓴 방명록 조회
     */
    @GetMapping("/{hostMemberNo}")
    private ResponseEntity getGuestbook(@RequestHeader Map<String, Object> requestHeader, @PathVariable int hostMemberNo) {
        int visitorMemberNo = getMemberNoFromRequestHeader(requestHeader);
        Optional<Guestbook> found = guestbookService.findGuestBook(hostMemberNo, visitorMemberNo);

        if (found.isPresent()) {
            Guestbook guestbook = found.get();
            GuestbookResponse guestbookResponse = new GuestbookResponse(guestbook);

            return new ResponseEntity(guestbookResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity("작성한 방명록이 없습니다.\n", HttpStatus.NO_CONTENT);
        }
    }

    /**
     * 방명록 등록
     */
    @PostMapping("/{hostMemberNo}")
    private ResponseEntity saveGuestbook(@RequestHeader Map<String, Object> requestHeader, @RequestBody Map<String, Object> requestBody, @PathVariable int hostMemberNo) {
        int visitorMemberNo = getMemberNoFromRequestHeader(requestHeader);
        String content = (String) requestBody.get(MyConstant.CONTENT);

        GuestbookForm guestbookForm = GuestbookForm.builder()
                .hostMemberNo(hostMemberNo)
                .visitorMemberNo(visitorMemberNo)
                .content(content)
                .build();

        Guestbook guestbook = guestbookService.saveGuestbook(guestbookForm);
        return ResponseEntity.ok().build();
    }

    /**
     * 방명록 수정
     */
    @PatchMapping("/{hostMemberNo}")
    private ResponseEntity updateGuestbook(@RequestHeader Map<String, Object> requestHeader, @RequestBody Map<String, Object> requestBody, @PathVariable int hostMemberNo) {
        int visitorMemberNo = getMemberNoFromRequestHeader(requestHeader);
        String content = (String) requestBody.get(MyConstant.CONTENT);

        GuestbookForm guestbookForm = GuestbookForm.builder()
                .hostMemberNo(hostMemberNo)
                .visitorMemberNo(visitorMemberNo)
                .content(content)
                .build();

        guestbookService.updateGuestbook(guestbookForm);
        return ResponseEntity.ok().build();
    }

    /**
     * 방명록 삭제
     */
    @PatchMapping("/{hostMemberNo}/del")
    private ResponseEntity updateGuestbook(@RequestHeader Map<String, Object> requestHeader, @PathVariable int hostMemberNo) {
        int visitorMemberNo = getMemberNoFromRequestHeader(requestHeader);

        int guestbookNo = guestbookService.findGuestBook(hostMemberNo, visitorMemberNo).get().getGuestbookNo();
        guestbookService.deleteGuestbook(guestbookNo);

        return ResponseEntity.noContent().build();
    }
}