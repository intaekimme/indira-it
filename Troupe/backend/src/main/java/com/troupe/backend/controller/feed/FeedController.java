package com.troupe.backend.controller.feed;

import com.troupe.backend.domain.feed.FeedLike;
import com.troupe.backend.dto.feed.FeedInsertRequest;
import com.troupe.backend.dto.feed.FeedResponse;
import com.troupe.backend.service.feed.FeedILikeService;
import com.troupe.backend.service.feed.FeedSaveService;
import com.troupe.backend.service.feed.FeedService;
import com.troupe.backend.util.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

//    @Autowired
//    private final S3FileUploadService service;

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
    
    // 조건별 피드 목록받기
    @GetMapping("/list/{change}")
    public ResponseEntity selectAllFeed(@PathVariable String change, @RequestParam int memberNo) throws IOException {
        try{
            List<FeedResponse> feedResponse = feedService.selectAll(change, memberNo);
            return new ResponseEntity(feedResponse, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed select All FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    // 공연 등록자가 등록한 피드 목록 불러오기
    @GetMapping("/list/performer/{memberNo}")
    public ResponseEntity selectAllFeedByPerformer(@PathVariable int memberNo) throws IOException {
        try{
            List<FeedResponse> feedResponse = feedService.selectAllByMember(memberNo);
            return new ResponseEntity(feedResponse, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed select All FAIL", HttpStatus.BAD_REQUEST);
        }
    }

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
    @PostMapping
    public ResponseEntity insertFeed(@RequestParam("images") List<MultipartFile> images,
                                 @RequestParam("memberNo")int memberNo,
                                 @RequestParam("content") String content,
                                 @RequestParam("tags") List<String> tags) throws IOException {
        try{
            FeedInsertRequest request = new FeedInsertRequest();
            request.setImages(images);
            request.setMemberNo(memberNo);
            request.setContent(content);
            request.setTags(tags);
            feedService.insert(request);
            return new ResponseEntity("Feed Insert SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed Insert FAIL", HttpStatus.BAD_REQUEST);
        }
    }
    // responsebody로 수정
    @PatchMapping
    public ResponseEntity updateFeed(@RequestParam("feedNo") int feedNo,
                                 @RequestParam(name = "images",required = false) List<MultipartFile> images,
                                 @RequestParam(name = "deletedImages", required = false) List<Integer> imageNo,
                                 @RequestParam(name = "content", required = false) String content,
                                 @RequestParam(name = "tags", required = false) List<String> tags) throws IOException {

        try {
            FeedInsertRequest request = new FeedInsertRequest();
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
    @PatchMapping("/{feedNo}/like")
    public ResponseEntity likeFeed(@PathVariable int feedNo, @RequestParam int memberNo) throws IOException {
        try {
            boolean check = feedILikeService.insert(memberNo,feedNo);
            return new ResponseEntity(check, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed Like FAIL", HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{feedNo}/save")
    public ResponseEntity saveFeed(@PathVariable int feedNo, @RequestParam int memberNo) throws IOException {
        try {
            boolean check = feedSaveService.insert(memberNo,feedNo);
            return new ResponseEntity(check, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed Save FAIL", HttpStatus.BAD_REQUEST);
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
