package com.troupe.backend.dto.guestbook;

import com.troupe.backend.domain.member.Guestbook;
import com.troupe.backend.dto.member.response.MemberInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestbookResponse {
    private MemberInfoResponse hostMemberInfoResponse;
    private MemberInfoResponse visitorMemberInfoResponse;
    private String content;
    private Date createdTime;
    private boolean isRemoved;

    public GuestbookResponse(Guestbook guestbook) {
        this.hostMemberInfoResponse = new MemberInfoResponse(guestbook.getHostMember());
        this.visitorMemberInfoResponse = new MemberInfoResponse(guestbook.getVisitorMember());
        this.content = guestbook.getContent();
        this.createdTime = guestbook.getCreatedTime();
        this.isRemoved = guestbook.isRemoved();
    }
}
