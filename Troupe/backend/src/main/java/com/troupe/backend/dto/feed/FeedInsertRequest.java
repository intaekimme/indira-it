package com.troupe.backend.dto.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.repository.feed.FeedImageRepository;
import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.service.feed.S3FileUploadService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

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

    @Autowired
    S3FileUploadService s3FileUploadService;

    public FeedInsertRequest(List<String> tags, int memberNo, String content, boolean removed, Date createdTime, List<MultipartFile> images) {
        this.tags = tags;
        this.memberNo = memberNo;
        this.content = content;
        this.isRemoved = removed;
        this.createdTime = createdTime;
        this.images = images;
    }

//    public FeedInsertRequest feedInsertRequest(List<Tag> tags){
//        return FeedInsertRequest.builder().tags().
//    }

    //dto와 FeedEntity 연결 (current time 생략)
    public Feed toFeedEntity(){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        System.out.println("toFeedEntity -> memberNo: "+memberNo);
        System.out.println("toFeedEntity -> memberRepository: "+memberRepository.getById(memberNo));
        return Feed.builder()
                .member(memberRepository.getById(memberNo))
                .content(content)
                .isRemoved(false)
                .build();
    }
    //dto와 FeedImageEntity 연결 (해당 Feed 매개변수로)
    public List<FeedImage> toFeedImageEntity(Feed feed) throws  Exception{
        if(feed!=null) System.out.println("toFeedImageEntity -> getFeedNo: "+feed.getFeedNo());
        else   System.out.println("toFeedImageEntity null");

        List<FeedImage> list = new ArrayList<>();
        for (MultipartFile file: images){
            // multipartFile을 url로 바꿔주는 service후 list에 FeedImage 객체로 저장
            String url = s3FileUploadService.upload(file,"feed");
            System.out.println("오나 url? "+url);
            list.add(FeedImage.builder().feed(feed).imageUrl(url).build());
        }
        return list;
    }

    //tagEntity 연결
    public List<Tag> toTagEntity(){

        List<Tag> list = new ArrayList<>();
        for(String tagName:tags){
            list.add(Tag.builder().name(tagName).build());
            System.out.println("toTagImageEntity -> "+tagName);
        }
        return list;
    }
}

