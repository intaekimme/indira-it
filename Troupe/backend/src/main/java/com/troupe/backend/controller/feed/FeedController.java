package com.troupe.backend.controller.feed;

import com.troupe.backend.dto.comment.CommentForm;
import com.troupe.backend.dto.comment.CommentResponse;
import com.troupe.backend.dto.feed.FeedForm;
import com.troupe.backend.dto.feed.FeedResponse;
import com.troupe.backend.dto.feed.FeedUpdateVO;
import com.troupe.backend.dto.feed.FeedVO;
import com.troupe.backend.service.comment.CommentService;
import com.troupe.backend.service.feed.FeedILikeService;
import com.troupe.backend.service.feed.FeedSaveService;
import com.troupe.backend.service.feed.FeedService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@Api("피드 REST API")
@RestController
@RequiredArgsConstructor
@EnableWebMvc
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private final FeedService feedService;

    @Autowired
    private final FeedILikeService feedILikeService;

    @Autowired
    private  final FeedSaveService feedSaveService;

    @Autowired
    private final CommentService commentService;

    @Operation(summary = "피드 상세 조회", description = "파라미터: 피드 번호")
    @GetMapping("/{feedNo}")
    public ResponseEntity selectFeed(@PathVariable int feedNo) throws IOException {
        try{
            FeedResponse feedResponse  = feedService.select(feedNo);
            return new ResponseEntity(feedResponse, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed select FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "조건별 피드 목록 조회(+공연자가 등록한 피드 목록 조회)", description = "파라미터: {change} = [all,save,follow] (후에 profileController로 이전-피드저장부분)")
    @GetMapping("/list/{change}")
    public ResponseEntity selectAllFeed(@PathVariable(required = false) String change, Principal principal, @RequestParam(name = "memberNo",required = false) Integer memberNo,
                                        int pageNumber)  {
        try{
            PageRequest pageRequest = PageRequest.of(pageNumber,6);
//            Slice<FeedResponse> feedResponses = null;
            List<FeedResponse> feedResponse = new ArrayList<>();
            if(change.equals("all")) feedResponse = feedService.selectAll(change, 0,pageRequest);
            else if(memberNo==null ) feedResponse = feedService.selectAll(change, Integer.parseInt(principal.getName()),pageRequest);
            else feedResponse = feedService.selectAllByMember(memberNo,pageRequest);
            return new ResponseEntity(feedResponse, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed select All FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    // responsebody로 수정
    @Operation(summary = "피드 등록", description = "파라미터: 이미지 파일들, 멤버번호, 내용, 태그명들")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity insertFeed(Principal principal,@ModelAttribute @Valid FeedVO feedVO) throws IOException {
        try{
            FeedForm feedForm = new FeedForm();
            if(principal.getName()!=null) feedForm.setMemberNo(Integer.parseInt(principal.getName()));
            if(feedVO.getContent()==null) feedVO.setContent("");
            if(feedVO.getTags()!=null) feedForm.setTags(feedVO.getTags());
            if(feedVO.getImages()!=null) feedForm.setImages(feedVO.getImages());
            feedForm.setContent(feedVO.getContent());
            feedService.insert(feedForm);
            return new ResponseEntity("Feed Insert SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed Insert FAIL", HttpStatus.BAD_REQUEST);
        }
    }
    // responsebody로 수정
    @Operation(summary = "피드 수정", description = "파라미터: 피드 번호, 이미지파일들, 삭제된 이미지 url들, 변경된 내용, 태그리스트들")
    @PostMapping(value = "/{feedNo}/modify",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateFeed(@PathVariable int feedNo,@ModelAttribute @Valid FeedUpdateVO feedUpdateVO) throws IOException {
        try {
            FeedForm request = new FeedForm();
            request.setFeedNo(feedNo);
            if(feedUpdateVO.getImages()!=null)  request.setImages(feedUpdateVO.getImages());
            if(feedUpdateVO.getImageNo()!=null) request.setImageNo(feedUpdateVO.getImageNo());
            if(feedUpdateVO.getImageNo()!=null) request.setContent(feedUpdateVO.getContent());
            if(feedUpdateVO.getTags()!=null)  request.setTags(feedUpdateVO.getTags());
//            System.out.println(request.getFeedNo()+" "+request.getImageNo()+" "+request.getContent()+" "+request.getImages()+" "+request.getTags()+" "+request.getTagNo());
            feedService.update(request);
            return new ResponseEntity("Feed update SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed update FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 삭제", description = "파라미터: 피드 번호")
    @PatchMapping("/{feedNo}/del")
    public ResponseEntity deleteFeed(@PathVariable int feedNo) throws IOException {
        try {
            feedService.delete(feedNo);
            return new ResponseEntity("Feed delete SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed delete FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 좋아요 스위칭", description = "파라미터: 피드번호, 멤버번호 (처음엔 insert, 그 뒤는 update)")
    @PatchMapping("/{feedNo}/like")
    public ResponseEntity likeFeed(@PathVariable int feedNo, Principal principal) throws IOException {
        try {
            boolean check = feedILikeService.insert(Integer.parseInt(principal.getName()),feedNo);
            return new ResponseEntity(check, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed Like FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 저장 스위칭", description = "파라미터: 피드 번호, 멤버 번호")
    @PatchMapping("/{feedNo}/save")
    public ResponseEntity saveFeed(@PathVariable int feedNo, Principal principal) throws IOException {
        try {
            boolean check = feedSaveService.insert(Integer.parseInt(principal.getName()),feedNo);
            return new ResponseEntity(check, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed Save FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    // 아래로 피드 댓글
    @Operation(summary = "피드 댓글 등록", description = "파라미터: 피드번호, 멤버번호, 내용, 부모댓글번호-선택")
    @PostMapping("/{feedNo}/comment")
    public ResponseEntity insertComment(Principal principal,
                                     @RequestParam String content,
                                     @RequestParam(required = false) Integer parentCommentNo,
                                        @PathVariable int feedNo) throws IOException {
        try{
            CommentForm request = new CommentForm();
            request.setMemberNo(Integer.parseInt(principal.getName()));
            request.setContent(content);
            if(parentCommentNo!=null)  request.setParentCommentNo(parentCommentNo);
            else request.setParentCommentNo(0);
            request.setFeedNo(feedNo);
            commentService.insert(request);
            return new ResponseEntity("Comment Insert SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Comment Insert FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 댓글 수정", description = "파라미터: 피드 번호, 댓글 번호, 수정된 내용")
    @PatchMapping("/{feedNo}/comment/{commentNo}")
    public ResponseEntity updateComment(@PathVariable int feedNo,
                                        @PathVariable int commentNo,
                                        @RequestParam String content) throws IOException {
        try{
            CommentForm request = new CommentForm();
            request.setCommentNo(commentNo);
            request.setContent(content);
            request.setFeedNo(feedNo);
            commentService.update(request);
            return new ResponseEntity("Comment update SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Comment update FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 댓글 삭제", description = "파라미터: 피드 번호, 댓글 번호")
    @PatchMapping("/{feedNo}/comment/{commentNo}/del")
    public ResponseEntity deleteComment(@PathVariable int feedNo,
                                        @PathVariable int commentNo) throws IOException {
        try{
            CommentForm request = new CommentForm();
            request.setCommentNo(commentNo);
            request.setFeedNo(feedNo);
            commentService.delete(commentNo);
            return new ResponseEntity("Comment delete SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Comment delete FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 댓글 목록(대댓글x)", description = "파라미터: 피드 번호")
    @GetMapping("/{feedNo}/comment/list")
    public ResponseEntity selectAllComment(@PathVariable int feedNo) throws IOException {
        try{
            List<CommentResponse> responses = commentService.selectAll(feedNo);
            return new ResponseEntity(responses, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Comment selectAll FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 대댓글 목록", description = "파라미터: 피드 번호, 댓글 번호")
    @GetMapping("/{feedNo}/comment/{commentNo}")
    public ResponseEntity selectAllCommentByParent(@PathVariable int feedNo,
                                                   @PathVariable int commentNo) throws IOException {
        try{
            List<CommentResponse> responses = commentService.selectAllByParent(commentNo);
            return new ResponseEntity(responses, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Comment selectAllByParent FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드별 좋아요 개수 ", description = "파라미터: 피드 번호")
    @GetMapping("/{feedNo}/like")
    public ResponseEntity getCountLike (@PathVariable int feedNo) throws IOException {
        try{
            int totalCount = feedILikeService.countTotalLike(feedNo);
            return new ResponseEntity(totalCount, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("like totalcount FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 태그 검색 ", description = "파라미터: 태그명들 ")
    @GetMapping("/search")
    public ResponseEntity tagSearch (@RequestParam List<String> tags) throws IOException {
        try{
             List<FeedResponse> list = feedService.selectAllBySearch(tags);
            return new ResponseEntity(list, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("search FAIL", HttpStatus.BAD_REQUEST);
        }
    }

}
