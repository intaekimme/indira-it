package com.troupe.backend.util;

public final class MyConstant {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String MESSAGE = "message";

    // 유저 관련
    public static final String MEMBER_NO = "memberNo";
    public static final String EMAIL = "email";
    public static final String NICKNAME = "nickname";

    public static final String PASSWORD = "password";
    public static final String MEMBER_TYPE = "memberType";

    public static final String IS_REMOVED = "isRemoved";
    public static final String DESCRIPTION = "description";

    public static final String PROFILE_IMAGE_URL = "profileImageUrl";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String CONTENT = "content";

    // 팔로우 관련
    public static final String FAN_LIST = "fanList";
    public static final String FAN_COUNT = "fanCount";
    public static final String STAR_LIST = "starList";
    public static final String IS_FOLLOWING = "isFollowing";

    // 관심 관련
    public static final String TOP_4_INTEREST_TAG_LIST = "top4InterestTagList";
    public static final String INTEREST_CATEGORY_LIST = "interestCategoryList";

    // 호감도 관련
    public static final String EXP = "exp";
    public static final String LEVEL = "level";
    public static final String REQUIRED_EXP_NOW = "requiredExpNow";
    public static final String REQUIRED_EXP_NEXT = "requiredExpNext";
    public static final String RANK = "rank";

    public static final String TOP_3_STARS = "top3Stars";
    public static final String TOP_100_FANS = "top100Fans";

    // 호감도 경험치 상승량
    public static final int EXP_FOLLOW = 10;
    public static final int EXP_PERFORMANCE_SAVE = 5;
    public static final int EXP_FEED_SAVE = 3;
    public static final int EXP_FEED_LIKE = 1;
    public static final int EXP_FEED_COMMENT = 1;
    public static final int EXP_PERFORMANCE_REVIEW = 1;


    // 파일 서버 주소
    public static final String FILE_SERVER_URL = "https://s3.ap-northeast-2.amazonaws.com/hongjoo.troupe.project/";
    public static final String EMAIL_SENDER_ADDRESS = "troupetest0001@gmail.com";

    public static final String WEB_SITE_URL = "https://i7a804.p.ssafy.io";

    // 공연 관련
    public static final String PREV = "공연예정";
    public static final String ING = "공연중";
    public static final String END = "공연종료";

    // 아바타 관련
    public static final String CLOTHES_LIST = "clothesList";
    public static final String EYE_LIST = "eyeList";
    public static final String HAIR_LIST = "hairList";
    public static final String MOUTH_LIST = "mouthList";
    public static final String NOSE_LIST = "noseList";
    public static final String SHAPE_LIST = "shapeList";

}
