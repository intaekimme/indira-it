package com.troupe.backend.dto.converter;

import com.troupe.backend.domain.comment.Comment;
import com.troupe.backend.dto.comment.CommentForm;
import com.troupe.backend.dto.comment.CommentResponse;
import com.troupe.backend.repository.comment.CommentRepository;
import com.troupe.backend.util.MyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    @Autowired
    CommentRepository commentRepository;

    // entity -> comment dto 상세
    public CommentResponse commentResponse(Comment comment){
        CommentResponse response = new CommentResponse();
        response.setCommentNo(comment.getCommentNo());
        response.setMemberNo(comment.getMember().getMemberNo());
        response.setFeedNo(comment.getFeed().getFeedNo());
        if(comment.getParentComment()!=null) response.setParentCommentNo(comment.getParentComment().getCommentNo());
        response.setNickname(comment.getMember().getNickname());
        response.setProfileImageUrl(MyConstant.FILE_SERVER_URL+comment.getMember().getProfileImageUrl());
        response.setCreatedTime(comment.getCreatedTime());
        response.setContent(comment.getContent());
        response.setRemoved(comment.isRemoved());
        response.setModified(comment.isModified());
        return response;
    }


    // update: dto->entity
    public Comment toEntity(CommentForm request){
        Comment comment = commentRepository.findById(request.getCommentNo()).get();
        Comment updateComment = Comment.builder().commentNo(comment.getCommentNo())
                .feed(comment.getFeed())
                .parentComment(comment.getParentComment())
                .createdTime(comment.getCreatedTime())
                .isModified(true)
                .member(comment.getMember())
                .content(request.getContent())
                .build();
        return updateComment;
    }
}
