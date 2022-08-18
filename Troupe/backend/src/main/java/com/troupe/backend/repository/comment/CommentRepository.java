package com.troupe.backend.repository.comment;

import com.troupe.backend.domain.comment.Comment;
import com.troupe.backend.domain.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByFeedAndParentCommentIsNullOrderByCreatedTimeDesc(Feed feed);

    List<Comment> findByParentCommentOrderByCreatedTimeDesc(Comment parentComment);
}