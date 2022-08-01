package com.troupe.backend.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private int memberNo;
    private int feedNo;
    private int parentCommentNo;
    private String nickname;
    private String profileImageUrl;
    private Date createdTime;
    private String content;
    private boolean isRemoved;
    private boolean isModified;

}
