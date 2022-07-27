package com.troupe.backend.service.feed;

import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    @Autowired
    FeedRepository feedRepository;

    @Autowired
    MemberRepository memberRepository;

    public void insert(){
        
    }



}
