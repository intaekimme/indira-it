package com.troupe.backend.contorller2.feed;

import com.troupe.backend.dto.feed.FeedInsertRequest;
import com.troupe.backend.service.feed.FeedService;
import com.troupe.backend.service.feed.S3FileUploadService;
import lombok.RequiredArgsConstructor;
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
    private final FeedService feedService;

    private final S3FileUploadService service;
    @PostMapping
    public String insert(@RequestParam("images") MultipartFile file,
                         @RequestParam("memberNo")int memberNo,
                         @RequestParam("content") String content,
                         @RequestParam("tags") String tag) throws IOException {
        try{
            FeedInsertRequest request = new FeedInsertRequest();
            List<MultipartFile> files = new ArrayList<>();
            List<String> tags = new ArrayList<>();
            tags.add(tag);
            files.add(file);
            request.setImages(files);
            request.setMemberNo(memberNo);
            request.setContent(content);
            request.setTags(tags);
            feedService.insert(request);
            return "success";
        }catch (Exception e){
            System.out.println(e);
        }
        return "fail";
    }
    @PostMapping("/test")
    public String test(@RequestParam MultipartFile file) throws Exception{
        try{
            service.upload(file,"static");
            return "success";
        }catch (Exception e){
            return "fail";
        }
    }
}
