package com.troupe.backend.service.comment;

import com.troupe.backend.domain.comment.Comment;
import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.comment.CommentForm;
import com.troupe.backend.dto.comment.CommentResponse;
import com.troupe.backend.dto.converter.CommentConverter;
import com.troupe.backend.repository.comment.CommentRepository;
import com.troupe.backend.repository.feed.FeedRepository;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.service.member.LikabilityService;
import com.troupe.backend.util.MyConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

    private final FeedRepository feedRepository;

    private final CommentConverter commentConverter;

    private final LikabilityService likabilityService;


    public  CommentResponse insert(CommentForm request){
        try{
            Member member = memberRepository.findById(request.getMemberNo()).get();
            Feed feed = feedRepository.findById(request.getFeedNo()).get();
            LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            Date now = java.sql.Timestamp.valueOf(localDateTime);
            Comment comment = null;
            if(request.getParentCommentNo()!=0){
                Comment parentComment = commentRepository.findById(request.getParentCommentNo()).get();
                comment = Comment.builder().feed(feed).parentComment(parentComment).createdTime(now).member(member).content(request.getContent()).build();
            }
            else{
                comment = Comment.builder().feed(feed).createdTime(now).member(member).content(request.getContent()).build();
            }
            Comment cmt = commentRepository.save(comment);

            // 호감도 갱신
            int starMemberNo = feed.getMember().getMemberNo();
            int fanMemberNo = member.getMemberNo();
            likabilityService.updateExp(starMemberNo, fanMemberNo, MyConstant.EXP_FEED_COMMENT);

            return commentConverter.commentResponse(cmt);
        }catch (Exception e){
            log.info(e.toString());
        }
        return null;
    }

    public CommentResponse update(CommentForm request){
        Optional<Comment> comment = commentRepository.findById(request.getCommentNo());

        if(comment.isPresent()){
            Comment updateComment = commentConverter.toEntity(request);
            Comment cmt = commentRepository.save(updateComment);
            return  commentConverter.commentResponse(cmt);
        }else return null;

    }

    public void delete(int commentNo){
        Optional<Comment> found = commentRepository.findById(commentNo);
        if(found.isPresent()){
            Comment comment = found.get();
            comment.setRemoved(true);

            // 호감도 갱신
            int starMemberNo = comment.getFeed().getMember().getMemberNo(); // 피드 작성자 번호
            int fanMemberNo = comment.getMember().getMemberNo(); // 댓글 작성자 번호
            likabilityService.updateExp(starMemberNo, fanMemberNo, -MyConstant.EXP_FEED_COMMENT);

            commentRepository.save(comment);
        }else return;
    }

    public CommentResponse select(int commentNo){
        Comment comment = commentRepository.findById(commentNo).get();
        return commentConverter.commentResponse(comment);
    }

    // 해당 댓글의 대댓글 목록
    public List<CommentResponse> selectAllByParent(int parentCommentNo){
        List<Comment> comments = commentRepository.findByParentCommentOrderByCreatedTimeDesc(commentRepository.findById(parentCommentNo).get());
        List<CommentResponse> commentResponses = new ArrayList<>();
        for(Comment comment: comments){
            commentResponses.add(select(comment.getCommentNo()));
        }
        return commentResponses;
    }

    // 피드별 댓글목록
    public List<CommentResponse> selectAll(int feedNo){
        List<CommentResponse> list = new ArrayList<>();
        List<Comment> comments = commentRepository.findByFeedAndParentCommentIsNullOrderByCreatedTimeDesc(feedRepository.findById(feedNo).get());
        for(Comment comment:comments){
            list.add(select(comment.getCommentNo()));
        }
        return list;
    }
}
