package com.troupe.backend.repository.feed;

import com.troupe.backend.domain.character.*;
import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.domain.member.MemberType;
import com.troupe.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.text.ParseException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class FeedTest {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void insert() throws ParseException {
        CharacterClothes clothes = new CharacterClothes(1,"url");
        CharacterEye eyes = new CharacterEye(1,"url");
        CharacterHair hair = new CharacterHair(1,"url");
        CharacterMouth mouth = new CharacterMouth(1,"url");
        CharacterNose nose = new CharacterNose(1,"url");
        CharacterShape shape = new CharacterShape(1,"url");
        Member member  = new Member(3,"email","password","nickname","description", MemberType.PERFORMER,"url",false, clothes,eyes,hair,mouth,nose,shape);
        Feed feed = new Feed(1,member,"insertTest",false,null);

//        Member member = memberRepository.getById(3);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//        Feed feed = Feed.builder().member(member).content("content").isRemoved(false).createdTime(dateFormat.parse("20220724")).build();
//        feedRepository.save(feed);

    }
}
