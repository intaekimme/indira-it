package com.troupe.backend.dto.comment;

import com.troupe.backend.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private int commentNo;
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
