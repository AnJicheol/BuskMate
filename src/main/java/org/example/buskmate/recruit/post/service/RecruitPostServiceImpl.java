package org.example.buskmate.recruit.post.service;

import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.repository.BandRepository;
import org.example.buskmate.recruit.post.domain.RecruitPost;
import org.example.buskmate.recruit.post.domain.RecruitPostStatus;
import org.example.buskmate.recruit.post.dto.*;
import org.example.buskmate.recruit.post.repository.RecruitPostRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RecruitPostServiceImpl implements RecruitPostService {
    private final RecruitPostRepository recruitPostRepository;
    private final BandRepository bandRepository;

    @Transactional
    @Override
    public CreateRecruitPostResponseDto create(CreateRecruitPostRequestDto req, String currentUserId){
        Band band = bandRepository.findByBandIdAndStatusActive(req.getBandId());
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

    @Transactional(readOnly = true)
    @Override
    public RecruitPostDetailResponseDto getDetail(String postId){
        return recruitPostRepository.findDetail(postId)
                .orElseThrow(() -> new IllegalArgumentException("모집 글을 찾을 수 없습니다."));

    }

    @Transactional(readOnly = true)
    @Override
    public List<RecruitPostListDto> getActiveList(){
        return recruitPostRepository.findAllByStatus(RecruitPostStatus.OPEN);
    }

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
}
