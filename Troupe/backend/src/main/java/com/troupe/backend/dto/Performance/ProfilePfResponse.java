package com.troupe.backend.dto.Performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilePfResponse {
    String perfPoster;
    String perfName;
    Date perfStartDate;
    Date perfEndDate;
}
