package org.example.buskmate.band.repository;

import org.example.buskmate.band.domain.RecruitPost;
import org.example.buskmate.band.domain.RecruitPostStatus;
import org.example.buskmate.band.dto.recruitpost.RecruitPostDetailResponseDto;
import org.example.buskmate.band.dto.recruitpost.RecruitPostListDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface RecruitPostRepository extends JpaRepository<RecruitPost, Long> {

    Optional<RecruitPost> findByPostId(String postId);

    List<RecruitPost> findAllByBandId(String bandId);

    Optional<RecruitPostDetailResponseDto> findDetail(String postId);

    List<RecruitPostListDto> findAllByStatus(RecruitPostStatus status);
}
