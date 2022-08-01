package com.troupe.backend.dto.converter;

import com.troupe.backend.domain.comment.Comment;
import com.troupe.backend.dto.comment.CommentForm;
import com.troupe.backend.dto.comment.CommentResponse;
import com.troupe.backend.repository.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    @Autowired
    CommentRepository commentRepository;
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
