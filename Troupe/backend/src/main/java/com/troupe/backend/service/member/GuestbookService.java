package com.troupe.backend.service.member;

import com.troupe.backend.repository.member.GuestbookRepository;
import com.troupe.backend.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestbookService {
    private GuestbookRepository guestbookRepository;

    private MemberRepository memberRepository;

    @Autowired
    public void setGuestbookRepository(GuestbookRepository guestbookRepository) {
        this.guestbookRepository = guestbookRepository;
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
