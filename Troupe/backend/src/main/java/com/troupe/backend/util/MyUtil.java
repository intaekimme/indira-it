package com.troupe.backend.util;

import java.util.Map;

public class MyUtil {
    public static int getMemberNoFromRequestHeader(Map<String, Object> requestHeader) {
        String s = (String) requestHeader.get(MyConstant.MEMBER_NO.toLowerCase());
        return Integer.parseInt(s);
    }
}
