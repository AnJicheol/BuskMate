package org.example.buskmate.band.dto.recruitapplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.band.domain.RecruitApplicationStatus;

import java.time.LocalDateTime;
/**
 * 모집 글 지원 생성(Apply) 요청에 대한 응답 DTO입니다.
 *
 * <p>
 * {@code RecruitApplyResponseDto}는 사용자가 특정 모집 글에
 * 지원을 완료한 직후 반환되는 응답 객체로,
 * 생성된 지원 정보의 핵심 내용을 제공합니다.
 * </p>
 *
 * <h3>사용 목적</h3>
 * <ul>
 *   <li>지원 성공 여부 확인</li>
 *   <li>생성된 지원 식별자 전달</li>
 *   <li>초기 지원 상태 및 시각 확인</li>
 * </ul>
 *
 * <p>
 * 이 DTO는 지원 생성 이후의 결과 전달에 초점을 맞춘
 * 응답 전용(Read-only) 객체입니다.
 * </p>
 */
@Getter
@Builder
@AllArgsConstructor
public class RecruitApplyResponseDto {
    private String applicationId;
    private String postId;
    private String applicantId;
    private RecruitApplicationStatus status;
    private LocalDateTime appliedAt;
}
