package com.troupe.backend.service.likability;

import com.troupe.backend.domain.likability.Likability;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.likability.LikabilityRepository;
import com.troupe.backend.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikabilityService {
    private LikabilityRepository likabilityRepository;

    private MemberRepository memberRepository;

    @Autowired
    public void setLikabilityRepository(LikabilityRepository likabilityRepository) {
        this.likabilityRepository = likabilityRepository;
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 스타 멤버 번호와 팬 멤버 번호를 받아서 둘의 호감도를 리턴
     */
    public Optional<Likability> findByStarMemberNoAndFanMemberNo(int starMemberNo, int fanMemberNo) {
        Member starMember = memberRepository.findById(starMemberNo).get();
        Member fanMember = memberRepository.findById(fanMemberNo).get();

        return likabilityRepository.findByStarMemberAndFanMember(starMember, fanMember);
    }

    /**
     * 스타 멤버 번호를 받아서 호감도 리스트를 리턴
     */
    public List<Likability> findByStarMemberNo(int starMemberNo) {
        Member starMember = memberRepository.findById(starMemberNo).get();

        return likabilityRepository.findAllByStarMember(starMember);
    }

    /**
     * 팬 멤버 번호를 받아서 호감도 리스트를 리턴
     */
    public List<Likability> findByFanMemberNo(int fanMemberNo) {
        Member fanMember = memberRepository.findById(fanMemberNo).get();

        return likabilityRepository.findAllByStarMember(fanMember);
    }

    /**
     * 스타 멤버 번호, 팬 멤버 번호, 증가/감소시킬 값을 받아서 호감도를 변경한 후 결과를 리턴
     */
    public Likability updateExp(int starMemberNo, int fanMemberNo, int val) {
        Likability likability = findByStarMemberNoAndFanMemberNo(starMemberNo, fanMemberNo).get();
        likability.setExp(likability.getExp() + val);
        return likability;
    }
}
