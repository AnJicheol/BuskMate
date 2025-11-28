package org.example.buskmate.band.dto.recruitpost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.band.domain.RecruitPostStatus;

@Getter
@Builder
@AllArgsConstructor
public class RecruitPostStatusResponseDto {
    private String postId;
    private RecruitPostStatus status;
}
