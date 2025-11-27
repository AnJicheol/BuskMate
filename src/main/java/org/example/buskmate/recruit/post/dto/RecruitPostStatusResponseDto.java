package org.example.buskmate.recruit.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.recruit.post.domain.RecruitPostStatus;

@Getter
@Builder
@AllArgsConstructor
public class RecruitPostStatusResponseDto {
    private String postId;
    private RecruitPostStatus status;
}
