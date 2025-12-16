package org.example.buskmate.band.service;

import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.domain.*;
import org.example.buskmate.band.dto.recruitapplication.RecruitApplicationListItemDto;
import org.example.buskmate.band.dto.recruitapplication.RecruitApplyResponseDto;
import org.example.buskmate.band.repository.RecruitApplicationRepository;
import org.example.buskmate.band.repository.RecruitPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 모집 글 지원(Recruit Application)과 관련된
 * 비즈니스 로직을 구현한 서비스 클래스입니다.
 *
 * <p>
 * {@code RecruitApplicationServiceImpl}은
 * 사용자의 모집 글 지원 생성/취소와
 * 밴드 리더의 지원 수락/거절 처리,
 * 그리고 지원자 목록 조회에 대한
 * 핵심 유스케이스를 담당합니다.
 * </p>
 *
 * <h3>책임 범위</h3>
 * <ul>
 *   <li>모집 글 상태 검증 (OPEN 여부)</li>
 *   <li>중복 지원 방지</li>
 *   <li>지원자/리더 권한 검증</li>
 *   <li>지원 상태 전이(WAITING → ACCEPTED / REJECTED / DELETED)</li>
 *   <li>지원 수락 시 밴드 멤버 편입 처리</li>
 * </ul>
 *
 * <p>
 * 모든 상태 변경 로직은 트랜잭션 내에서 수행되며,
 * 실제 데이터 삭제가 아닌 상태 변경 방식을 사용합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor

public class RecruitApplicationServiceImpl implements RecruitApplicationService {
    private final RecruitApplicationRepository recruitApplicationRepository;
    private final RecruitPostRepository recruitPostRepository;
    private final BandMemberService bandMemberService;

    /**
     * 사용자가 특정 모집 글에 지원합니다.
     *
     * <p>
     * 다음 조건을 만족해야 지원이 가능합니다.
     * </p>
     * <ul>
     *   <li>모집 글이 존재할 것</li>
     *   <li>모집 글 상태가 {@link RecruitPostStatus#OPEN}일 것</li>
     *   <li>동일 모집 글에 중복 지원하지 않았을 것</li>
     * </ul>
     *
     * @param postId         지원할 모집 글 식별자
     * @param currentUserId  현재 로그인한 사용자 ID
     * @return 생성된 지원 정보 응답 DTO
     */
    @Transactional
    @Override
    public RecruitApplyResponseDto apply(String postId, String currentUserId) {
        //모집글 조회
        RecruitPost post = recruitPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집 글을 찾을 수 없습니다."));

        //모집중인지 확인하기..
        if (post.getStatus() != RecruitPostStatus.OPEN) {
            throw new IllegalStateException("지원할 수 없는 모집 글입니다.");
        }

        boolean alreadyApplied = recruitApplicationRepository.existsByRecruitPost_PostIdAndApplicantId(postId, currentUserId);

        //중복지원 금지
        if (alreadyApplied) {
            throw new IllegalStateException("이미 이 모집 글에 지원한 상태입니다.");
        }

        String applicationId = UlidCreator.getUlid().toString();

        RecruitApplication application = RecruitApplication.builder()
                .applicationId(applicationId)
                .applicantId(currentUserId)
                .recruitPost(post)
                .build();

        recruitApplicationRepository.save(application);

        return RecruitApplyResponseDto.builder()
                .applicationId(application.getApplicationId())
                .postId(postId)
                .applicantId(currentUserId)
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();

    }

    /**
     * 사용자가 본인의 모집 글 지원을 취소합니다.
     *
     * <p>
     * 다음 조건을 만족해야 취소가 가능합니다.
     * </p>
     * <ul>
     *   <li>본인이 작성한 지원일 것</li>
     *   <li>아직 수락되지 않은 지원일 것</li>
     * </ul>
     *
     * @param applicationId  취소할 지원 식별자
     * @param currentUserId  현재 로그인한 사용자 ID
     */
    @Transactional
    @Override
    public void delete(String applicationId, String currentUserId) {
        RecruitApplication application = recruitApplicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지원 내역을 찾을 수 없습니다."));

        if (!application.getApplicantId().equals(currentUserId)) {
            throw new AccessDeniedException("본인이 작성한 지원만 철회할 수 있습니다.");
        }

        if (application.getStatus() == RecruitApplicationStatus.ACCEPTED) {
            throw new IllegalStateException("이미 수락된 지원은 철회할 수 없습니다.");
        }

        application.delete();
    }


    /**
     * 밴드 리더가 특정 모집 글에 대한 지원을 수락합니다.
     *
     * <p>
     * 수락 처리 시,
     * 지원자는 자동으로 해당 밴드의 멤버로 편입됩니다.
     * </p>
     *
     * @param applicationId  수락할 지원 식별자
     * @param currentUserId  현재 로그인한 사용자 ID
     */
    @Transactional
    @Override
    public void accept(String applicationId, String currentUserId) {
        RecruitApplication application = recruitApplicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지원 내역을 찾을 수 없습니다."));

        RecruitPost post = application.getRecruitPost();

        if (!post.getBand().getLeaderId().equals(currentUserId)) {
            throw new AccessDeniedException("밴드장만 승인할 수 있습니다.");
        }

        if (application.getStatus() != RecruitApplicationStatus.WAITING) {
            throw new IllegalStateException("승인 대기중인 지원만 승인할 수 있습니다.");
        }

        application.accept();

        bandMemberService.addMemberAccepted(post.getBand(), application.getApplicantId());


    }


    /**
     * 밴드 리더가 특정 모집 글에 대한 지원을 거절합니다.
     *
     * @param applicationId  거절할 지원 식별자
     * @param currentUserId  현재 로그인한 사용자 ID
     */
    @Transactional
    @Override
    public void reject(String applicationId, String currentUserId) {
        RecruitApplication application = recruitApplicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지원 내역을 찾을 수 없습니다."));

        RecruitPost post = application.getRecruitPost();

        if(!post.getBand().getLeaderId().equals(currentUserId)){
            throw new AccessDeniedException("밴드장만 거절할 수 있습니다.");
        }
        if (application.getStatus() != RecruitApplicationStatus.WAITING) {
            throw new IllegalStateException("승인 대기중인 지원만 거절할 수 있습니다.");
        }

        application.reject();
    }


    /**
     * 특정 모집 글에 대한 지원 목록을
     * 페이지네이션 형태로 조회합니다.
     *
     * <p>
     * 해당 모집 글이 속한 밴드의 리더만
     * 지원자 목록을 조회할 수 있습니다.
     * </p>
     *
     * @param postId         모집 글 식별자
     * @param currentUserId  현재 로그인한 사용자 ID
     * @param pageable       페이지 정보
     * @return 페이지네이션된 지원 목록 DTO
     */
    @Transactional(readOnly = true)
    @Override
    public Page<RecruitApplicationListItemDto> getApplications(String postId, String currentUserId, Pageable pageable){
        RecruitPost post = recruitPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글을 찾을 수 없습니다."));

        if(!post.getBand().getLeaderId().equals(currentUserId)){
            throw new AccessDeniedException("밴드장만 지원자 목록을 조회할 수 있습니다.");
        }
        Page<RecruitApplication> page =
                recruitApplicationRepository.findByRecruitPost_PostId(postId, pageable);

        return page.map(app ->
                RecruitApplicationListItemDto.builder()
                        .applicationId(app.getApplicationId())
                        .applicantId(app.getApplicantId())
                        .status(app.getStatus())
                        .appliedAt(app.getAppliedAt())
                        .build()
        );
    }
}
