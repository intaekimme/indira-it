package com.troupe.backend.dto.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.feed.FeedImageRepository;
import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.member.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedInsertRequest {
    private List<String> tags;
    private int memberNo;
    private String content;
    private boolean isRemoved;
    private Date createdTime;
    private List<MultipartFile> images;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FeedImageRepository feedImageRepository;

    @Autowired
    FeedRepository feedRepository;

//    public FeedInsertRequest feedInsertRequest(List<Tag> tags){
//        return FeedInsertRequest.builder().tags().
//    }

    //dto와 FeedEntity 연결 (current time 생략)
    public Feed toFeedEntity(){
        return Feed.builder()
                .member(memberRepository.getById(memberNo))
                .content(content)
                .isRemoved(false)
                .build();
    }
    //dto와 FeedImageEntity 연결 (해당 Feed 매개변수로)
    public List<FeedImage> toFeedImageEntity(Feed feed){
        List<FeedImage> list = new ArrayList<>();
        for (MultipartFile files: images){
            String url = "change";
            // multipartFile을 url로 바꿔주는 service후 list에 FeedImage 객체로 저장
            list.add(FeedImage.builder().feed(feed).imageUrl(url).build());
        }
        return list;
    }

    //tagEntity 연결
    public List<Tag> toTagEntity(){
        List<Tag> list = new ArrayList<>();
        for(String tagName:tags){
            list.add(Tag.builder().name(tagName).build());
        }
        return list;
    }
}

