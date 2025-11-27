package org.example.buskmate.recruit.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.recruit.post.domain.RecruitPostStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RecruitPostDetailResponseDto {
    private String postId;
    private String bandId;
    private String title;
    private String content;
    private RecruitPostStatus status;
    private LocalDateTime createdAt;

    public RecruitPostDetailResponseDto() {}
}
