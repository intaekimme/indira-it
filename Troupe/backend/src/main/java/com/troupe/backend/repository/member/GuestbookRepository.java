package com.troupe.backend.repository.member;

import com.troupe.backend.domain.member.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<Guestbook, Integer> {
}