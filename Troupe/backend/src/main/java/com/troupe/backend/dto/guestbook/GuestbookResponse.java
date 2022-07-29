package com.troupe.backend.dto.guestbook;

import com.troupe.backend.domain.member.Guestbook;
import com.troupe.backend.dto.member.MemberInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestbookResponse {
    MemberInfoResponse hostMemberInfoResponse;
    MemberInfoResponse visitorMemberInfoResponse;
    String content;

    public GuestbookResponse(Guestbook guestbook) {
        this.hostMemberInfoResponse = new MemberInfoResponse(guestbook.getHostMember());
        this.visitorMemberInfoResponse = new MemberInfoResponse(guestbook.getVisitorMember());
        this.content = guestbook.getContent();
    }
}
