package org.example.buskmate.band.dto.recruitapplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.band.domain.RecruitApplicationStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RecruitApplicationListItemDto {

    private String applicationId;
    private String applicantId;
    private RecruitApplicationStatus status;
    private LocalDateTime appliedAt;
}