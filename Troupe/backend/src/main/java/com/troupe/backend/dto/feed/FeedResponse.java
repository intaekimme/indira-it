package com.troupe.backend.dto.feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponse {
    private int feedNo;
    private int memberNo;
    private String nickname;
    private List<String> images;
    private List<String> tags;
    private String content;
    private int likeTotalCount;
    public void setLikeTotalCount(int count){this.likeTotalCount = count;}
}
