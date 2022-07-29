package com.troupe.backend.service.member;

import com.troupe.backend.domain.avatar.*;
import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.avatar.*;
import com.troupe.backend.repository.avatar.*;
import com.troupe.backend.repository.member.MemberRepository;
import com.troupe.backend.service.feed.S3FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AvatarService {
    private MemberRepository memberRepository;
    private AvatarClothesRepository avatarClothesRepository;
    private AvatarEyeRepository avatarEyeRepository;
    private AvatarNoseRepository avatarNoseRepository;
    private AvatarMouthRepository avatarMouthRepository;
    private AvatarShapeRepository avatarShapeRepository;
    private AvatarHairRepository avatarHairRepository;
    private S3FileUploadService s3FileUploadService;

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

    @Autowired
    public void setS3FileUploadService(S3FileUploadService s3FileUploadService) {
        this.s3FileUploadService = s3FileUploadService;
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

    public AvatarClothes findClothesById(int clothesNo) {
        return avatarClothesRepository.findById(clothesNo).get();
    }

    public AvatarEye findEyeById(int eyeNo) {
        return avatarEyeRepository.findById(eyeNo).get();
    }

    public AvatarHair findHairById(int hairNo) {
        return avatarHairRepository.findById(hairNo).get();
    }

    public AvatarMouth findMouthById(int mouthNo) {
        return avatarMouthRepository.findById(mouthNo).get();
    }

    public AvatarNose findNoseById(int noseNo) {
        return avatarNoseRepository.findById(noseNo).get();
    }

    public AvatarShape findShapeById(int shapeNo) {
        return avatarShapeRepository.findById(shapeNo).get();
    }

    /**
     * 기본 아바타를 리턴
     */
    public Avatar findDefaultAvatar() {
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
    public Avatar findAvatar(AvatarForm avatarForm) {
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
    public Avatar updateMemberAvatar(int memberNo, AvatarForm avatarForm) {
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

    // 이하는 아바타 각 파트의 이미지 등록, 수정 삭제
    public AvatarClothes saveClothes(AvatarClothesForm avatarClothesForm) throws IOException {
        // 이미지 서버에 업로드
        String clothesUrl = s3FileUploadService.upload(avatarClothesForm.getClothesImage(), "avatar/clothes");

        // 객체 빌드
        AvatarClothes clothes = AvatarClothes.builder()
                .clothesNo(avatarClothesForm.getClothesNo())
                .clothesUrl(clothesUrl)
                .build();

        // DB에 저장
        return avatarClothesRepository.save(clothes);
    }

    public AvatarClothes updateClothes(AvatarClothesForm avatarClothesForm) throws IOException {
        AvatarClothes foundClothes = avatarClothesRepository.findById(avatarClothesForm.getClothesNo()).get();

        // 이미지 서버에 업로드
        String clothesUrl = s3FileUploadService.upload(avatarClothesForm.getClothesImage(), "avatar/clothes");

        // DB 업데이트
        foundClothes.setClothesUrl(clothesUrl);

        return avatarClothesRepository.save(foundClothes);
    }

    public void deleteClothes(int clothesNo) {
        AvatarClothes found = avatarClothesRepository.findById(clothesNo).get();
        avatarClothesRepository.delete(found);
    }
    public AvatarEye saveEye(AvatarEyeForm avatarEyeForm) throws IOException {
        // 이미지 서버에 업로드
        String eyeUrl = s3FileUploadService.upload(avatarEyeForm.getEyeImage(), "avatar/eye");

        // 객체 빌드
        AvatarEye eye = AvatarEye.builder()
                .eyeNo(avatarEyeForm.getEyeNo())
                .eyeUrl(eyeUrl)
                .build();

        // DB에 저장
        return avatarEyeRepository.save(eye);
    }

    public AvatarEye updateEye(AvatarEyeForm avatarEyeForm) throws IOException {
        AvatarEye foundEye = avatarEyeRepository.findById(avatarEyeForm.getEyeNo()).get();

        // 이미지 서버에 업로드
        String eyeUrl = s3FileUploadService.upload(avatarEyeForm.getEyeImage(), "avatar/eye");

        // DB 업데이트
        foundEye.setEyeUrl(eyeUrl);

        return avatarEyeRepository.save(foundEye);
    }

    public void deleteEye(int eyeNo) {
        AvatarEye found = avatarEyeRepository.findById(eyeNo).get();
        avatarEyeRepository.delete(found);
    }

    public AvatarHair saveHair(AvatarHairForm avatarHairForm) throws IOException {
        // 이미지 서버에 업로드
        String hairUrl = s3FileUploadService.upload(avatarHairForm.getHairImage(), "avatar/hair");

        // 객체 빌드
        AvatarHair hair = AvatarHair.builder()
                .hairNo(avatarHairForm.getHairNo())
                .hairUrl(hairUrl)
                .build();

        // DB에 저장
        return avatarHairRepository.save(hair);
    }

    public AvatarHair updateHair(AvatarHairForm avatarHairForm) throws IOException {
        AvatarHair foundHair = avatarHairRepository.findById(avatarHairForm.getHairNo()).get();

        // 이미지 서버에 업로드
        String hairUrl = s3FileUploadService.upload(avatarHairForm.getHairImage(), "avatar/hair");

        // DB 업데이트
        foundHair.setHairUrl(hairUrl);

        return avatarHairRepository.save(foundHair);
    }

    public void deleteHair(int hairNo) {
        AvatarHair found = avatarHairRepository.findById(hairNo).get();
        avatarHairRepository.delete(found);
    }

    public AvatarMouth saveMouth(AvatarMouthForm avatarMouthForm) throws IOException {
        // 이미지 서버에 업로드
        String mouthUrl = s3FileUploadService.upload(avatarMouthForm.getMouthImage(), "avatar/mouth");

        // 객체 빌드
        AvatarMouth mouth = AvatarMouth.builder()
                .mouthNo(avatarMouthForm.getMouthNo())
                .mouthUrl(mouthUrl)
                .build();

        // DB에 저장
        return avatarMouthRepository.save(mouth);
    }

    public AvatarMouth updateMouth(AvatarMouthForm avatarMouthForm) throws IOException {
        AvatarMouth foundMouth = avatarMouthRepository.findById(avatarMouthForm.getMouthNo()).get();

        // 이미지 서버에 업로드
        String mouthUrl = s3FileUploadService.upload(avatarMouthForm.getMouthImage(), "avatar/mouth");

        // DB 업데이트
        foundMouth.setMouthUrl(mouthUrl);

        return avatarMouthRepository.save(foundMouth);
    }

    public void deleteMouth(int mouthNo) {
        AvatarMouth found = avatarMouthRepository.findById(mouthNo).get();
        avatarMouthRepository.delete(found);
    }

    public AvatarNose saveNose(AvatarNoseForm avatarNoseForm) throws IOException {
        // 이미지 서버에 업로드
        String noseUrl = s3FileUploadService.upload(avatarNoseForm.getNoseImage(), "avatar/nose");

        // 객체 빌드
        AvatarNose nose = AvatarNose.builder()
                .noseNo(avatarNoseForm.getNoseNo())
                .noseUrl(noseUrl)
                .build();

        // DB에 저장
        return avatarNoseRepository.save(nose);
    }

    public AvatarNose updateNose(AvatarNoseForm avatarNoseForm) throws IOException {
        AvatarNose foundNose = avatarNoseRepository.findById(avatarNoseForm.getNoseNo()).get();

        // 이미지 서버에 업로드
        String noseUrl = s3FileUploadService.upload(avatarNoseForm.getNoseImage(), "avatar/nose");

        // DB 업데이트
        foundNose.setNoseUrl(noseUrl);

        return avatarNoseRepository.save(foundNose);
    }

    public void deleteNose(int noseNo) {
        AvatarNose found = avatarNoseRepository.findById(noseNo).get();
        avatarNoseRepository.delete(found);
    }

    public AvatarShape saveShape(AvatarShapeForm avatarShapeForm) throws IOException {
        // 이미지 서버에 업로드
        String shapeUrl = s3FileUploadService.upload(avatarShapeForm.getShapeImage(), "avatar/shape");

        // 객체 빌드
        AvatarShape shape = AvatarShape.builder()
                .shapeNo(avatarShapeForm.getShapeNo())
                .shapeUrl(shapeUrl)
                .build();

        // DB에 저장
        return avatarShapeRepository.save(shape);
    }

    public AvatarShape updateShape(AvatarShapeForm avatarShapeForm) throws IOException {
        AvatarShape foundShape = avatarShapeRepository.findById(avatarShapeForm.getShapeNo()).get();

        // 이미지 서버에 업로드
        String shapeUrl = s3FileUploadService.upload(avatarShapeForm.getShapeImage(), "avatar/shape");

        // DB 업데이트
        foundShape.setShapeUrl(shapeUrl);

        return avatarShapeRepository.save(foundShape);
    }

    public void deleteShape(int shapeNo) {
        AvatarShape found = avatarShapeRepository.findById(shapeNo).get();
        avatarShapeRepository.delete(found);
    }


}
