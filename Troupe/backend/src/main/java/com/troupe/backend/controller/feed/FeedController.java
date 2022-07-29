package com.troupe.backend.controller.feed;

import com.troupe.backend.dto.feed.FeedInsertRequest;
import com.troupe.backend.service.feed.FeedService;
import com.troupe.backend.service.feed.S3FileUploadService;
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
    private final S3FileUploadService service;
    // responsebody로 수정
    @PostMapping
    public ResponseEntity insert(@RequestParam("images") List<MultipartFile> images,
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
    public ResponseEntity update(@RequestParam("feedNo") int feedNo,
                                 @RequestParam(name = "images",required = false) List<MultipartFile> images,
                                 @RequestParam(name = "deletedImages", required = false) List<Integer> imageNo,
                                 @RequestParam(name = "content", required = false) String content,
                                 @RequestParam(name = "tags", required = false) List<String> tags,
                                 @RequestParam(name = "deletedTags",required = false) List<Integer> tagNo) throws IOException {

        try {
            FeedInsertRequest request = new FeedInsertRequest();
            request.setFeedNo(feedNo);
            request.setImages(images);
            request.setImageNo(imageNo);
            request.setContent(content);
            request.setTags(tags);
            request.setTagNo(tagNo);
//            System.out.println(request.getFeedNo()+" "+request.getImageNo()+" "+request.getContent()+" "+request.getImages()+" "+request.getTags()+" "+request.getTagNo());
            feedService.update(request);
            return new ResponseEntity("Feed update SUCCESS", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity("Feed update FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/test")
    public String  delete(@RequestParam String url) throws  IOException {
        service.deleteFile(url);
        return "success";
    }
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
