package com.troupe.backend.dto.performance.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilePfSaveResponse {
    int memberNo;
    String nickname;
    String perfPoster;
    String perfName;
    Date perfStartDate;
    Date perfEndDate;
}
