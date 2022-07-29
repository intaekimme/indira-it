package com.troupe.backend.dto.guestbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestbookForm {
    int hostMemberNo;
    int visitorMemberNo;
    String content;
}
