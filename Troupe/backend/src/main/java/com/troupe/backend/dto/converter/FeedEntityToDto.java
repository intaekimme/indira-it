package com.troupe.backend.dto.converter;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.FeedImage;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.dto.feed.FeedInsertRequest;
import com.troupe.backend.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedEntityToDto {

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
}
