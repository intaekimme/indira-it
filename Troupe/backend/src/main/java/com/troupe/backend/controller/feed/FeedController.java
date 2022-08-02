package com.troupe.backend.controller.feed;

import com.troupe.backend.domain.feed.Feed;
import com.troupe.backend.dto.comment.CommentForm;
import com.troupe.backend.dto.comment.CommentResponse;
import com.troupe.backend.dto.feed.FeedForm;
import com.troupe.backend.dto.feed.FeedResponse;
import com.troupe.backend.service.comment.CommentService;
import com.troupe.backend.service.feed.FeedILikeService;
import com.troupe.backend.service.feed.FeedSaveService;
import com.troupe.backend.service.feed.FeedService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

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
            return new ResponseEntity(feedResponse, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed select FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "조건별 피드 목록 조회", description = "파라미터: {change} = [all,save,follow] (후에 profileController로 이전-피드저장부분)")
    @GetMapping("/list/{change}")
    public ResponseEntity selectAllFeed(@PathVariable String change, Principal principal) throws IOException {
        try{
            List<FeedResponse> feedResponse = feedService.selectAll(change, Integer.parseInt(principal.getName()));
            return new ResponseEntity(feedResponse, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed select All FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "공연 등록자가 등록한 피드 목록 조회", description = "파라미터: 공연자 멤버 번호(후에 profileController로 이전)")
    @GetMapping("/{profileMemberNo}/myfeed/list")
    public ResponseEntity selectAllFeedByPerformer(@PathVariable(name = "profileMemberNo") int memberNo) throws IOException {
        try{
            List<FeedResponse> feedResponse = feedService.selectAllByMember(memberNo);
            return new ResponseEntity(feedResponse, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed select All FAIL", HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "태그로 인한 피드 검색", description = "파라미터: 태그명 리스트")
    @GetMapping("/search")
    public ResponseEntity searchFeeds (@RequestParam(name = "tags") List<String> tags) throws IOException {
        try {
            List<FeedResponse> feedResponse = feedService.selectAllBySearch(tags);
            return new ResponseEntity(feedResponse, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed search FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    // responsebody로 수정
    @Operation(summary = "피드 등록", description = "파라미터: 이미지 파일들, 멤버번호, 내용, 태그명들")
    @PostMapping
    public ResponseEntity insertFeed(Principal principal,
                                     @RequestPart("images") List<MultipartFile> images,
                                     @RequestBody(required = false) FeedForm feedForm) throws IOException {
        try{
            feedForm.setMemberNo(Integer.parseInt(principal.getName()));
            feedForm.setImages(images);
            feedService.insert(feedForm);
            return new ResponseEntity("Feed Insert SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed Insert FAIL", HttpStatus.BAD_REQUEST);
        }
    }
    // responsebody로 수정
    @Operation(summary = "피드 수정", description = "파라미터: 피드 번호, 이미지파일들, 삭제된 이미지 url들, 변경된 내용, 태그리스트들")
    @PatchMapping("/{feedNo}/modify")
    public ResponseEntity updateFeed(@PathVariable int feedNo,
                                 @RequestParam(name = "images",required = false) List<MultipartFile> images,
                                 @RequestParam(name = "deletedImages", required = false) List<Integer> imageNo,
                                 @RequestParam(name = "content", required = false) String content,
                                 @RequestParam(name = "tags", required = false) List<String> tags) throws IOException {

        try {
            FeedForm request = new FeedForm();
            request.setFeedNo(feedNo);
            request.setImages(images);
            request.setImageNo(imageNo);
            request.setContent(content);
            request.setTags(tags);
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

    @Operation(summary = "피드 좋아요 스위칭(처음엔 insert, 그 뒤는 update)", description = "파라미터: 피드번호, 멤버번호")
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

    @Operation(summary = "피드 저장", description = "파라미터: 피드 번호, 멤버 번호")
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
            return new ResponseEntity(responses, HttpStatus.CREATED);
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
            return new ResponseEntity(responses, HttpStatus.CREATED);
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
            return new ResponseEntity(totalCount, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Comment selectAllByParent FAIL", HttpStatus.BAD_REQUEST);
        }
    }
//    @PatchMapping("/test")
//    public String  delete(@RequestParam String url) throws  IOException {
//        service.deleteFile(url);
//        return "success";
//    }
//    @PostMapping("/test")
//    public String test(@RequestParam MultipartFile file) throws Exception{
//        try{
////            service.upload(file,"static");
//            return  service.upload(file,"static");
//        }catch (Exception e){
//            return "fail";
//        }
//    }
}
