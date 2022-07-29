package com.troupe.backend.dto.feed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedInsertRequest {
    private int feedNo;
    private List<String> tags;
    private List<Integer> imageNo;
    private List<MultipartFile> images;
    private List<Integer> tagNo;
    private int memberNo;
    private String content;
    private boolean isRemoved;
    private Date createdTime;

    public FeedInsertRequest(List<String> tags, int memberNo, String content, boolean removed, Date createdTime, List<MultipartFile> images) {
        this.tags = tags;
        this.memberNo = memberNo;
        this.content = content;
        this.isRemoved = removed;
        this.createdTime = createdTime;
        this.images = images;
    }
}

