package org.example.buskmate.band.service;

import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.domain.BandStatus;
import org.example.buskmate.band.dto.recruitpost.*;
import org.example.buskmate.band.repository.BandRepository;
import org.example.buskmate.band.domain.RecruitPost;
import org.example.buskmate.band.domain.RecruitPostStatus;
import org.example.buskmate.band.repository.RecruitPostRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 모집 글(Recruit Post)과 관련된
 * 비즈니스 로직을 구현한 서비스 클래스입니다.
 *
 * <p>
 * {@code RecruitPostServiceImpl}은 밴드 리더가
 * 팀원 모집을 위해 모집 글을 생성, 수정, 마감, 삭제하는
 * 핵심 유스케이스를 담당합니다.
 * </p>
 *
 * <h3>책임 범위</h3>
 * <ul>
 *   <li>밴드 존재 여부 및 활성 상태 검증</li>
 *   <li>밴드 리더 권한 검증</li>
 *   <li>모집 글 생성 및 조회</li>
 *   <li>모집 글 수정 및 상태 변경(마감 / 삭제)</li>
 * </ul>
 *
 * <p>
 * 모든 상태 변경 로직은 트랜잭션 내에서 수행되며,
 * 실제 데이터 삭제가 아닌 상태 변경 방식을 사용합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class RecruitPostServiceImpl implements RecruitPostService {
    private final RecruitPostRepository recruitPostRepository;
    private final BandRepository bandRepository;

    /**
     * 새로운 모집 글을 생성합니다.
     *
     * <p>
     * 다음 조건을 만족해야 모집 글 생성이 가능합니다.
     * </p>
     * <ul>
     *   <li>밴드가 존재하고 ACTIVE 상태일 것</li>
     *   <li>요청 사용자가 해당 밴드의 리더일 것</li>
     * </ul>
     *
     * @param req            모집 글 생성 요청 정보
     * @param currentUserId  현재 로그인한 사용자 ID
     * @return 생성된 모집 글의 기본 정보 응답 DTO
     */
    @Transactional
    @Override
    public CreateRecruitPostResponseDto create(CreateRecruitPostRequestDto req, String currentUserId){
        Band band = bandRepository.findByBandIdAndStatus(req.getBandId(), BandStatus.ACTIVE);
        if(band == null){
            throw new IllegalStateException("밴드를 찾을 수 없습니다");
        }

        if (!band.getLeaderId().equals(currentUserId)) {
            throw new AccessDeniedException("밴드장만 모집 글을 생성할 수 있습니다.");
        }

        String postId= UlidCreator.getUlid().toString();

        RecruitPost post = RecruitPost.builder()
                .postId(postId)
                .band(band)
                .title(req.getTitle())
                .content(req.getContent())
                .build();

        recruitPostRepository.save(post);

        return new CreateRecruitPostResponseDto(
                post.getPostId(),
                post.getBand().getBandId(),
                post.getTitle()
        );
    }
    /**
     * 모집 글 ID를 기준으로 모집 글의 상세 정보를 조회합니다.
     *
     * <p>
     * DTO 프로젝션을 사용하여
     * 조회 전용 데이터를 반환합니다.
     * </p>
     *
     * @param postId 조회할 모집 글 식별자
     * @return 모집 글 상세 정보 응답 DTO
     */
    @Transactional(readOnly = true)
    @Override
    public RecruitPostDetailResponseDto getDetail(String postId){
        return recruitPostRepository.findDetail(postId)
                .orElseThrow(() -> new IllegalArgumentException("모집 글을 찾을 수 없습니다."));

    }

    /**
     * 현재 모집 중(OPEN 상태)인 모집 글 목록을 조회합니다.
     *
     * <p>
     * 공개 API로 사용되며,
     * 로그인하지 않은 사용자도 조회할 수 있습니다.
     * </p>
     *
     * @return 활성 상태 모집 글 목록 DTO
     */
    @Transactional(readOnly = true)
    @Override
    public List<RecruitPostListDto> getActiveList(){
        return recruitPostRepository.findAllByStatus(RecruitPostStatus.OPEN);
    }
    /**
     * 모집 글 작성자(밴드 리더)가 모집 글을 수정합니다.
     *
     * <p>
     * 다음 조건을 만족해야 수정이 가능합니다.
     * </p>
     * <ul>
     *   <li>모집 글 상태가 OPEN일 것</li>
     *   <li>요청 사용자가 해당 밴드의 리더일 것</li>
     * </ul>
     *
     * @param postId         수정할 모집 글 식별자
     * @param req            모집 글 수정 요청 정보
     * @param currentUserId  현재 로그인한 사용자 ID
     * @return 수정된 모집 글 상세 정보 응답 DTO
     */
    @Transactional
    @Override
    public RecruitPostDetailResponseDto update(String postId, UpdateRecruitPostRequestDto req, String currentUserId){
        RecruitPost post= recruitPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("모집글을 찾을 수 없습니다"));


        if (post.getStatus() != RecruitPostStatus.OPEN) {
            throw new IllegalStateException("모집 중인 글만 수정할 수 있습니다.");
        }

        Band band= post.getBand();
        if (!band.getLeaderId().equals(currentUserId)) {
            throw new AccessDeniedException("밴드장만 모집 글을 수정할 수 있습니다.");
        }

        post.updateInfo(req.getTitle(), req.getContent());
        return RecruitPostDetailResponseDto.builder()
                .postId(post.getPostId())
                .bandId(post.getBand().getBandId())
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .build();
    }

    /**
     * 모집 글을 마감 상태로 변경합니다.
     *
     * <p>
     * 마감된 모집 글은 더 이상 지원을 받을 수 없으며,
     * 상태는 CLOSED로 변경됩니다.
     * </p>
     *
     * @param postId         마감할 모집 글 식별자
     * @param currentUserId  현재 로그인한 사용자 ID
     * @return 변경된 모집 글 상태 응답 DTO
     */
    @Transactional
    @Override
    public RecruitPostStatusResponseDto close(String postId, String currentUserId){
        RecruitPost post = recruitPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("모집 글을 찾을 수 없습니다."));

        if(post.getStatus() != RecruitPostStatus.OPEN){
            throw new IllegalStateException("모집 중인 글만 마감할 수 있습니다.");
        }

        Band band=post.getBand();
        if(!band.getLeaderId().equals(currentUserId)){
            throw new AccessDeniedException("밴드장만 모집 글을 마감할 수 있습니다.");
        }

        post.close();

        return RecruitPostStatusResponseDto.builder()
                .postId(post.getPostId())
                .status(post.getStatus())
                .build();
    }


    /**
     * 모집 글을 삭제(비활성화) 처리합니다.
     *
     * <p>
     * 실제 데이터 삭제가 아닌,
     * 모집 글 상태를 DELETED로 변경하여
     * 논리적으로 제거합니다.
     * </p>
     *
     * @param postId         삭제할 모집 글 식별자
     * @param currentUserId  현재 로그인한 사용자 ID
     * @return 변경된 모집 글 상태 응답 DTO
     */
    @Transactional
    @Override
    public RecruitPostStatusResponseDto delete(String postId, String currentUserId){
        RecruitPost post = recruitPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("모집 글을 찾을 수 없습니다."));

        Band band=post.getBand();
        if(!band.getLeaderId().equals(currentUserId)){
            throw new AccessDeniedException("밴드장만 모집 글을 삭제할 수 있습니다.");
        }

        post.delete();

        return RecruitPostStatusResponseDto.builder()
                .postId(post.getPostId())
                .status(post.getStatus())
                .build();
    }
}
