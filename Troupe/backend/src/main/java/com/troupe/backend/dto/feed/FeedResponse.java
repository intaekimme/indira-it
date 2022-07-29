package com.troupe.backend.dto.feed;

import java.util.HashMap;
import java.util.List;

public class FeedResponse {
    private int feedNo;
    private int memberNo;
    private String nickname;
    private HashMap<Integer,String> images;
    private HashMap<Integer, String> tags;
    private String content;

}
