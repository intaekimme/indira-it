package com.troupe.backend.dto.converter;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.feed.FeedResponse;
import com.troupe.backend.util.MyConstant;
import com.troupe.backend.util.S3FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FeedConverter {

    @Autowired
    S3FileUploadService s3FileUploadService;

//    @Autowired
//    FeedLikeRepositoryImpl feedLikeRepository;

    // response : Feed 상세
    public FeedResponse feedResponse(Feed feed, List<FeedImage> feedImage, List<Tag> taglist){
        FeedResponse response = new FeedResponse();
        response.setFeedNo(feed.getFeedNo());
        response.setMemberNo(feed.getMember().getMemberNo());
        response.setNickname(feed.getMember().getNickname());
        response.setProfileImageUrl(feed.getMember().getProfileImageUrl());
        response.setTags(tagList(taglist));
        response.setContent(feed.getContent());
        // totallikecount
//        response.setLikeTotalCount(feedLikeRepository.FeedLikeCount(feed.getFeedNo()));
        response.setImages(imageList(feedImage));
        response.setCreatedTime(feed.getCreatedTime());
        return response;
    }

    //tag -> String tag
    public List<String> tagList(List<Tag> taglist){
        List<String> tagList = new ArrayList<>();
        for(Tag tag:taglist){
            tagList.add(tag.getName());
        }
        return tagList;
    }

    //feedImage -> String url
    public List<String> imageList(List<FeedImage> imagelist){
        List<String> imageList = new ArrayList<>();
        for(FeedImage image:imagelist){
            imageList.add(MyConstant.FILE_SERVER_URL +image.getImageUrl());
        }
        return imageList;
    }
    // FeedInsertDto -> FeedEntity
    public Feed toFeedEntity(Member member, String content){
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Date now = java.sql.Timestamp.valueOf(localDateTime);
        return Feed.builder()
                .member(member)
                .content(content)
                .isRemoved(false)
                .createdTime(now)
                .build();
    }
    // FeedInsertDto -> FeedImageEntity
    public List<FeedImage> toFeedImageEntity(Feed feed, List<MultipartFile> images) throws  Exception{
        List<FeedImage> list = new ArrayList<>();
        for (MultipartFile file: images){
//            System.out.println("coming file? "+file.getOriginalFilename()+"   "+file);

            // multipartFile을 url로 바꿔주는 service후 list에 FeedImage 객체로 저장
            String url = s3FileUploadService.upload(file,"feed");
            list.add(FeedImage.builder().feed(feed).imageUrl(url).build());
        }
        return list;
    }

    // FeedInsertDto -> tagEntity
    public List<Tag> toTagEntity(List<String> tags){
        List<Tag> list = new ArrayList<>();
        for(String tagName:tags){
            list.add(Tag.builder().name(tagName.trim()).build());
//            System.out.println("toTagImageEntity -> "+tagName);
        }
        return list;
    }
}
