package org.example.buskmate.band.repository;

import org.example.buskmate.band.domain.RecruitApplication;
import org.example.buskmate.band.domain.RecruitApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 모집 글 지원(Recruit Application) 엔티티에 대한
 * 데이터 접근을 담당하는 Repository 인터페이스입니다.
 *
 * <p>
 * {@code RecruitApplicationRepository}는
 * 모집 글에 대한 사용자 지원 정보를 조회, 저장, 존재 여부 확인 등
 * 지원 도메인에 필요한 데이터 접근 기능을 제공합니다.
 * </p>
 *
 * <h3>주요 사용 시나리오</h3>
 * <ul>
 *   <li>지원 단건 조회 (applicationId 기준)</li>
 *   <li>모집 글별 지원 목록 조회</li>
 *   <li>지원 중복 여부 검증</li>
 *   <li>상태별 지원 조회</li>
 *   <li>페이지네이션 기반 지원 목록 조회</li>
 * </ul>
 */
public interface RecruitApplicationRepository extends JpaRepository<RecruitApplication, Long> {
    Optional<RecruitApplication> findByApplicationId(String applicationId);

    List<RecruitApplication> findAllByRecruitPost_PostId(String postId);
    List<RecruitApplication> findAllByRecruitPost_PostIdAndStatus(String postId, RecruitApplicationStatus status);

    boolean existsByRecruitPost_PostIdAndApplicantId(String postId, String applicantId);

    Page<RecruitApplication> findByRecruitPost_PostId(String postId, Pageable pageable);
}
