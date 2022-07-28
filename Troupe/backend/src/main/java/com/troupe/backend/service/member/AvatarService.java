package com.troupe.backend.service.member;

import com.troupe.backend.domain.avatar.*;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.avatar.Avatar;
import com.troupe.backend.dto.avatar.AvatarForm;
import com.troupe.backend.repository.avatar.*;
import com.troupe.backend.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AvatarService {
    private MemberRepository memberRepository;
    private AvatarClothesRepository avatarClothesRepository;
    private AvatarEyeRepository avatarEyeRepository;
    private AvatarNoseRepository avatarNoseRepository;
    private AvatarMouthRepository avatarMouthRepository;
    private AvatarShapeRepository avatarShapeRepository;
    private AvatarHairRepository avatarHairRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setAvatarClothesRepository(AvatarClothesRepository avatarClothesRepository) {
        this.avatarClothesRepository = avatarClothesRepository;
    }

    @Autowired
    public void setAvatarEyeRepository(AvatarEyeRepository avatarEyeRepository) {
        this.avatarEyeRepository = avatarEyeRepository;
    }

    @Autowired
    public void setAvatarNoseRepository(AvatarNoseRepository avatarNoseRepository) {
        this.avatarNoseRepository = avatarNoseRepository;
    }

    @Autowired
    public void setAvatarMouthRepository(AvatarMouthRepository avatarMouthRepository) {
        this.avatarMouthRepository = avatarMouthRepository;
    }

    @Autowired
    public void setAvatarShapeRepository(AvatarShapeRepository avatarShapeRepository) {
        this.avatarShapeRepository = avatarShapeRepository;
    }

    @Autowired
    public void setAvatarHairRepository(AvatarHairRepository avatarHairRepository) {
        this.avatarHairRepository = avatarHairRepository;
    }

    public List<AvatarClothes> findAllClothes() {
        return avatarClothesRepository.findAll();
    }

    public List<AvatarEye> findAllEye() {
        return avatarEyeRepository.findAll();
    }

    public List<AvatarHair> findAllHair() {
        return avatarHairRepository.findAll();
    }

    public List<AvatarMouth> findAllMouth() {
        return avatarMouthRepository.findAll();
    }

    public List<AvatarNose> findAllNose() {
        return avatarNoseRepository.findAll();
    }

    public List<AvatarShape> findAllShape() {
        return avatarShapeRepository.findAll();
    }

    private AvatarClothes findClothesById(int clothesNo) throws NoSuchElementException {
        return avatarClothesRepository.findById(clothesNo).get();
    }

    private AvatarEye findEyeById(int eyeNo) throws NoSuchElementException {
        return avatarEyeRepository.findById(eyeNo).get();
    }

    private AvatarHair findHairById(int hairNo) throws NoSuchElementException {
        return avatarHairRepository.findById(hairNo).get();
    }

    private AvatarMouth findMouthById(int mouthNo) throws NoSuchElementException {
        return avatarMouthRepository.findById(mouthNo).get();
    }

    private AvatarNose findNoseById(int noseNo) throws NoSuchElementException {
        return avatarNoseRepository.findById(noseNo).get();
    }

    private AvatarShape findShapeById(int shapeNo) throws NoSuchElementException {
        return avatarShapeRepository.findById(shapeNo).get();
    }

    /**
     * 기본 아바타를 리턴
     */
    public Avatar findDefaultAvatar() throws NoSuchElementException {
        AvatarClothes clothes = findClothesById(1);
        AvatarEye eye = findEyeById(1);
        AvatarHair hair = findHairById(1);
        AvatarMouth mouth = findMouthById(1);
        AvatarNose nose = findNoseById(1);
        AvatarShape shape = findShapeById(1);

        Avatar avatar = Avatar.builder()
                .avatarClothes(clothes).avatarEye(eye).avatarHair(hair)
                .avatarMouth(mouth).avatarNose(nose).avatarShape(shape)
                .build();

        return avatar;
    }

    /**
     * 요청받은 아바타폼으로 아바타를 찾아와서 리턴
     */
    public Avatar findAvatar(AvatarForm avatarForm) throws NoSuchElementException {
        AvatarClothes clothes = findClothesById(avatarForm.getClothesNo());
        AvatarEye eye = findEyeById(avatarForm.getEyeNo());
        AvatarHair hair = findHairById(avatarForm.getHairNo());
        AvatarMouth mouth = findMouthById(avatarForm.getMouthNo());
        AvatarNose nose = findNoseById(avatarForm.getNoseNo());
        AvatarShape shape = findShapeById(avatarForm.getShapeNo());

        Avatar avatar = Avatar.builder()
                .avatarClothes(clothes).avatarEye(eye).avatarHair(hair)
                .avatarMouth(mouth).avatarNose(nose).avatarShape(shape)
                .build();

        return avatar;
    }

    /**
     * 멤버의 아바타를 수정
     */
    public Avatar updateMemberAvatar(int memberNo, AvatarForm avatarForm) throws NoSuchElementException {
        Member foundMember = memberRepository.findById(memberNo).get();

        Avatar avatar = findAvatar(avatarForm);

        foundMember.setClothes(avatar.getAvatarClothes());
        foundMember.setEye(avatar.getAvatarEye());
        foundMember.setHair(avatar.getAvatarHair());
        foundMember.setMouth(avatar.getAvatarMouth());
        foundMember.setNose(avatar.getAvatarNose());
        foundMember.setShape(avatar.getAvatarShape());

        return avatar;
    }


}
