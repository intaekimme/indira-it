package com.troupe.backend.controller.member;

import com.troupe.backend.domain.avatar.*;
import com.troupe.backend.service.member.AvatarService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@Api("아바타 REST API")
@RequestMapping("/avatar")
@RestController
public class AvatarController {
    AvatarService avatarService;

    @Autowired
    public void setAvatarService(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/clothes")
    private ResponseEntity getClothesList() {
        List<AvatarClothes> result =  avatarService.findAllClothes();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/eye")
    private ResponseEntity getEyeList() {
        List<AvatarEye> result =  avatarService.findAllEye();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/hair")
    private ResponseEntity getHairList() {
        List<AvatarHair> result =  avatarService.findAllHair();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/mouth")
    private ResponseEntity getMouthList() {
        List<AvatarMouth> result =  avatarService.findAllMouth();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/nose")
    private ResponseEntity getNoseList() {
        List<AvatarNose> result =  avatarService.findAllNose();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/shape")
    private ResponseEntity getShapeList() {
        List<AvatarShape> result = avatarService.findAllShape();
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
