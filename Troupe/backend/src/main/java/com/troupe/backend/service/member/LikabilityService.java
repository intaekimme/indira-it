package com.troupe.backend.service.member;

import com.troupe.backend.domain.likability.Likability;
import com.troupe.backend.domain.likability.LikabilityLevel;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.likability.LikabilityResponse;
import com.troupe.backend.repository.likability.LikabilityLevelRepository;
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
    private final LikabilityLevelRepository likabilityLevelRepository;

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

    /**
     * 경험치에 해당하는 호감도 레벨을 리턴
     */
    public int getLikabilityLevel(int exp) {
        Optional<LikabilityLevel> found = likabilityLevelRepository.findTopByRequiredExpLessThanEqualOrderByLevelDesc(exp);

        if (found.isPresent()) {
            return found.get().getLevel();
        } else {
            return 0;
        }
    }

    /**
     * starMember에 대한 내 호감도 경험치가 exp일 때, 나의 순위를 리턴
     */
    public int getRank(int starMemberNo, int exp) {
        Member starMember = memberRepository.findById(starMemberNo).get();
        return likabilityRepository.countByStarMemberAndExpGreaterThan(starMember, exp).intValue();
    }

    /**
     * 호감도 레벨로 조회
     */
    public Optional<LikabilityLevel> findLikabilityLevelById(int level) {
        return likabilityLevelRepository.findById(level);
    }

    /**
     * fanMemberNo를 받아서, 이 팬이 가장 호감도가 높은 3개를 리턴
     */
    public List<Likability> getTop3StarList(int fanMemberNo) {
        Member fanMember = memberRepository.findById(fanMemberNo).get();
        List<Likability> ret = likabilityRepository.findTop3ByFanMemberOrderByExpDesc(fanMember);
        return ret;
    }

    /**
     * starMemberNo를 받아서, 이 스타에게 가장 호감도가 높은 100개를 리턴
     */
    public List<Likability> getTop100FanList(int starMemberNo) {
        Member starMember = memberRepository.findById(starMemberNo).get();
        List<Likability> ret = likabilityRepository.findTop100ByStarMemberOrderByExpDesc(starMember);
        return ret;
    }

    public LikabilityResponse getLikabilityResponse (int starMemberNo, int fanMemberNo) {
        // 현재 호감도 조회
        Optional<Likability> foundLikability = findByStarMemberNoAndFanMemberNo(starMemberNo, fanMemberNo);

        int level = 0;
        int exp = 0;
        int requiredExpNow = 0;
        int requiredExpNext = 0;

        // 현재 호감도의 레벨 조회
        if (foundLikability.isPresent()) {
            exp = foundLikability.get().getExp();
            level = getLikabilityLevel(exp);

            Optional<LikabilityLevel> foundNowLevel = likabilityLevelRepository.findById(level);

            if (foundNowLevel.isPresent()) {
                requiredExpNow = foundNowLevel.get().getRequiredExp();
            }
        }

        // 다음 레벨까지 필요 경험치 조회
        int nextLevel = level + 1;
        Optional<LikabilityLevel> foundNextLikabilityLevel = likabilityLevelRepository.findById(nextLevel);
        if (foundNextLikabilityLevel.isPresent()) {
            requiredExpNext = foundNextLikabilityLevel.get().getRequiredExp();
        }

        // 순위 조회
        int rank = getRank(starMemberNo, exp) + 1;

        LikabilityResponse likabilityResponse = LikabilityResponse.builder()
                .exp(exp)
                .level(level)
                .requiredExpNow(requiredExpNow)
                .requiredExpNext(requiredExpNext)
                .rank(rank)
                .build();

        return likabilityResponse;
    }
}
