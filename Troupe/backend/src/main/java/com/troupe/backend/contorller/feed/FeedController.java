package com.troupe.backend.contorller.feed;

import com.troupe.backend.dto.feed.FeedInsertRequest;
import com.troupe.backend.service.feed.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@RestController("/feed")
@RequiredArgsConstructor
@EnableWebMvc
public class FeedController {
    private final FeedService feedService;

    @PostMapping
    public String insert(@RequestBody FeedInsertRequest request) throws IOException {
        try{
            feedService.insert(request);
            return "success";
        }catch (Exception e){
            System.out.println(e);
        }
        return "fail";
    }
}
