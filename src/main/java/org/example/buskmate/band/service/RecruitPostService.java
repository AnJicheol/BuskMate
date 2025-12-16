package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.recruitpost.*;

import java.util.List;
/**
 * 모집 글(Recruit Post)과 관련된
 * 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 *
 * <p>
 * {@code RecruitPostService}는 밴드 리더가
 * 팀원 모집을 위해 모집 글을 생성, 수정, 마감, 삭제하고,
 * 사용자가 모집 글을 조회할 수 있도록 하는
 * 핵심 유스케이스를 정의합니다.
 * </p>
 *
 * <h3>책임 범위</h3>
 * <ul>
 *   <li>모집 글 생성 시 밴드 및 리더 권한 검증</li>
 *   <li>모집 글 상세 / 목록 조회</li>
 *   <li>모집 글 수정 권한 검증</li>
 *   <li>모집 글 상태 변경(마감 / 삭제)</li>
 * </ul>
 *
 * <p>
 * 인증 정보는 컨트롤러에서 전달받은
 * {@code currentUserId}를 기준으로 처리되며,
 * 실제 데이터 접근은 Repository 계층에 위임됩니다.
 * </p>
 */
public interface RecruitPostService {
    CreateRecruitPostResponseDto create(CreateRecruitPostRequestDto req, String currentUserId);

    RecruitPostDetailResponseDto getDetail(String postId);

    List<RecruitPostListDto> getActiveList();

    RecruitPostDetailResponseDto update(String postId, UpdateRecruitPostRequestDto req, String currentUserId);

    RecruitPostStatusResponseDto close(String postId, String currentUserId);

    RecruitPostStatusResponseDto delete(String postId, String currentUserId);
}
