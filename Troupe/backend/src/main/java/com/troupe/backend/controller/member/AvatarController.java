package com.troupe.backend.controller.member;

import com.troupe.backend.domain.avatar.*;
import com.troupe.backend.service.member.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AvatarController {
    AvatarService avatarService;

    @Autowired
    public void setAvatarService(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/avatar/clothes")
    private ResponseEntity getClothes() {
        List<AvatarClothes> result =  avatarService.findAllClothes();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/avatar/eye")
    private ResponseEntity getEye() {
        List<AvatarEye> result =  avatarService.findAllEye();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/avatar/hair")
    private ResponseEntity getHair() {
        List<AvatarHair> result =  avatarService.findAllHair();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/avatar/mouth")
    private ResponseEntity getMouth() {
        List<AvatarMouth> result =  avatarService.findAllMouth();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/avatar/nose")
    private ResponseEntity getNose() {
        List<AvatarNose> result =  avatarService.findAllNose();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/avatar/shape")
    private ResponseEntity getShape() {
        List<AvatarShape> result = avatarService.findAllShape();
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
