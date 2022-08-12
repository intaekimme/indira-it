package com.troupe.backend.dto.feed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class FeedVO {
    private List<String> tags;
    private List<MultipartFile> images;
    private String content;
}

