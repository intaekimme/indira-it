package com.troupe.backend.dto.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.feed.Tag;
import com.troupe.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

//    public FeedInsertRequest(List<String> tags, int memberNo, String content, boolean removed, Date createdTime, List<MultipartFile> images) {
//        this.tags = tags;
//        this.memberNo = memberNo;
//        this.content = content;
//        this.isRemoved = removed;
//        this.createdTime = createdTime;
//        this.images = images;
//    }

    //dto와 FeedEntity 연결
    public Feed toFeedEntity(Member member){
//        System.out.println("toFeedEntity -> memberNo: "+memberNo);
        return Feed.builder()
                .member(member)
                .content(content)
                .isRemoved(false)
                .build();
    }

}

