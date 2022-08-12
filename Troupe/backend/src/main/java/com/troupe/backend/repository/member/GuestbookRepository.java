package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Guestbook;
import com.troupe.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestbookRepository extends JpaRepository<Guestbook, Integer> {
    /**
     * 호스트와 방문자로 방명록 글 조회
     */
    Optional<Guestbook> findByHostMemberAndVisitorMember(Member hostMember, Member visitorMember);

    /**
     * 호스트의 방명록에 있는 모든 글 조회
     */
    List<Guestbook> findAllByHostMember(Member hostMember);

}