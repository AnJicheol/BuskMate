package org.example.buskmate.recruit.post.service;

import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.domain.BandStatus;
import org.example.buskmate.band.repository.BandRepository;
import org.example.buskmate.recruit.post.domain.RecruitPost;
import org.example.buskmate.recruit.post.dto.CreateRecruitPostRequestDto;
import org.example.buskmate.recruit.post.dto.CreateRecruitPostResponseDto;
import org.example.buskmate.recruit.post.dto.RecruitPostDetailResponseDto;
import org.example.buskmate.recruit.post.repository.RecruitPostRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RecruitPostServiceImpl implements RecruitPostService {
    private final RecruitPostRepository recruitPostRepository;
    private final BandRepository bandRepository;

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

    @Transactional(readOnly = true)
    @Override
    public RecruitPostDetailResponseDto getDetail(String postId){
        return recruitPostRepository.findDetail(postId)
                .orElseThrow(() -> new IllegalArgumentException("모집 글을 찾을 수 없습니다."));

    }
}
