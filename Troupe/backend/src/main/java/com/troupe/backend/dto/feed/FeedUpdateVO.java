package com.troupe.backend.dto.feed;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FeedUpdateVO {
    private List<String> tags;
    private List<MultipartFile> images;
    private String content;
    private List<Integer> imageNo;
}

