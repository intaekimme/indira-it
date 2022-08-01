package com.troupe.backend.dto.converter;

import com.troupe.backend.domain.member.Member;
import com.troupe.backend.dto.member.MemberInfoResponse;

import java.util.ArrayList;
import java.util.List;

public class MemberConverter {
    public static List<MemberInfoResponse> getMemberInfoResponseList (List<Member> members) {
        List<MemberInfoResponse> ret = new ArrayList<>();
        for (Member member : members) {
            MemberInfoResponse memberInfoResponse = new MemberInfoResponse(member);
            ret.add(memberInfoResponse);
        }

        return ret;
    }
}
