package org.example.buskmate.band.dto.recruitapplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.buskmate.band.domain.RecruitApplicationStatus;

import java.time.LocalDateTime;
/**
 * 모집 글에 대한 지원 목록 조회 시 사용되는 DTO입니다.
 *
 * <p>
 * {@code RecruitApplicationListItemDto}는 특정 모집 글에 들어온
 * 개별 지원 정보를 목록 형태로 제공하기 위해 사용되며,
 * 주로 밴드 리더가 지원자 목록을 조회할 때 반환됩니다.
 * </p>
 *
 * <h3>포함 정보</h3>
 * <ul>
 *   <li>지원 식별자</li>
 *   <li>지원자 사용자 ID</li>
 *   <li>지원 상태</li>
 *   <li>지원 일시</li>
 * </ul>
 *
 * <p>
 * 이 DTO는 엔티티를 직접 노출하지 않고,
 * 조회에 필요한 최소한의 정보만 전달하기 위한
 * 조회 전용(Read-only) 응답 객체입니다.
 * </p>
 */
@Getter
@Builder
@AllArgsConstructor
public class RecruitApplicationListItemDto {

    private String applicationId;
    private String applicantId;
    private RecruitApplicationStatus status;
    private LocalDateTime appliedAt;
}