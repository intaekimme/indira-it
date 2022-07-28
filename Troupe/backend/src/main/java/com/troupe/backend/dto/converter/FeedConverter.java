package com.troupe.backend.dto.converter;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.dto.feed.FeedInsertRequest;
import com.troupe.backend.service.feed.S3FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedConverter {

    @Autowired
    S3FileUploadService s3FileUploadService;

    // response : Feed 상세
    public FeedInsertRequest toFeedDto(Feed feed, List<FeedImage> feedImage, List<Tag> taglist, int memberNo){
        List<MultipartFile> images = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        for(FeedImage insert: feedImage){
            //parse to multipartFile
//            MultipartFile files =
//            images.add(files);

        }

        for(Tag insert: taglist){
            tags.add(insert.getName());
        }
        return new FeedInsertRequest(tags,memberNo,feed.getContent(),feed.isRemoved(),feed.getCreatedTime(),images);
    }

    // FeedInsertDto -> FeedImageEntity 연결 (해당 Feed 매개변수로)
    public List<FeedImage> toFeedImageEntity(Feed feed, List<MultipartFile> images) throws  Exception{
        List<FeedImage> list = new ArrayList<>();
        for (MultipartFile file: images){
            System.out.println("coming file? "+file.getOriginalFilename()+"   "+file);

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
            list.add(Tag.builder().name(tagName).build());
            System.out.println("toTagImageEntity -> "+tagName);
        }
        return list;
    }
}
