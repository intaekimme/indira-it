package com.troupe.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
public class IndexController {
    @RequestMapping("/")
    public ResponseEntity index() {
        return new ResponseEntity("index", HttpStatus.OK);
    }

}
