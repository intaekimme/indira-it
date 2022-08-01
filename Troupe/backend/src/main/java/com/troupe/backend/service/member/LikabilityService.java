package com.troupe.backend.service.member;

import com.troupe.backend.domain.likability.Likability;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.likability.LikabilityRepository;
import com.troupe.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikabilityService {
    private final LikabilityRepository likabilityRepository;

    private final MemberRepository memberRepository;

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
        Optional<Likability> found = findByStarMemberNoAndFanMemberNo(starMemberNo, fanMemberNo);

        // 기존 값이 있으면 그로부터 증감
        if (found.isPresent()) {
            Likability likability = found.get();
            likability.setExp(likability.getExp() + val);

            return likability;
        }

        // 기존에 없었던 경우 새 값 인서트
        else {
            Likability likability = Likability.builder()
                    .starMember(memberRepository.findById(starMemberNo).get())
                    .fanMember(memberRepository.findById(fanMemberNo).get())
                    .exp(val)
                    .build();
            likabilityRepository.save(likability);

            return likability;
        }


    }

}
