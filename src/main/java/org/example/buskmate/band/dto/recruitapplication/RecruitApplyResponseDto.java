package org.example.buskmate.band.dto.recruitapplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.band.domain.RecruitApplicationStatus;

@Getter
@Builder
@AllArgsConstructor
public class RecruitApplyResponseDto {
    private String applicationId;
    private String postId;
    private String applicantId;
    private RecruitApplicationStatus status;
}
