package org.example.buskmate.band.dto.recruitpost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.buskmate.band.domain.RecruitPostStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitPostDetailResponseDto {
    private String postId;
    private String bandId;
    private String title;
    private String content;
    private RecruitPostStatus status;
    private LocalDateTime createdAt;

}
