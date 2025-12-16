package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.recruitapplication.RecruitApplicationListItemDto;
import org.example.buskmate.band.dto.recruitapplication.RecruitApplyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 모집 글 지원(Recruit Application)과 관련된
 * 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 *
 * <p>
 * {@code RecruitApplicationService}는
 * 사용자의 모집 글 지원, 지원 취소,
 * 그리고 밴드 리더의 지원 수락/거절 처리와
 * 지원 목록 조회 기능을 제공합니다.
 * </p>
 *
 * <h3>책임 범위</h3>
 * <ul>
 *   <li>모집 글 지원 생성 및 중복 지원 검증</li>
 *   <li>지원 취소 권한 검증</li>
 *   <li>지원 수락/거절 시 리더 권한 검증</li>
 *   <li>모집 글별 지원 목록 조회</li>
 * </ul>
 *
 * <p>
 * 인증 정보는 컨트롤러에서 전달받은
 * {@code currentUserId}를 기준으로 처리되며,
 * 실제 데이터 접근은 Repository 계층에 위임됩니다.
 * </p>
 */
public interface RecruitApplicationService {

    RecruitApplyResponseDto apply(String postId, String currentUserId);

    void delete(String applicationId, String currentUserId);

    void accept(String applicationId, String currentUserId);

    void reject(String applicationId, String currentUserId);

    Page<RecruitApplicationListItemDto> getApplications(String postId, String currentUserId, Pageable pageable);
}
