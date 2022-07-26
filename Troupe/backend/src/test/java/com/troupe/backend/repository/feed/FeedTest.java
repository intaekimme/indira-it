package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
class FeedTest {

    @Autowired
    FeedRepository feedRepository;


//    @Test
//    public void insert() {
//        Feed feed = new Feed(,1,"insertTest",null,null);
//    }
}
