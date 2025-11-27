package org.example.buskmate.recruit.post.repository;

import org.example.buskmate.recruit.post.domain.RecruitPost;
import org.example.buskmate.recruit.post.dto.RecruitPostDetailResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface RecruitPostRepository extends JpaRepository<RecruitPost, Long> {

    Optional<RecruitPost> findByPostId(String postId);

    List<RecruitPost> findAllByBandId(String bandId);

    Optional<RecruitPostDetailResponseDto> findDetail(String postId);

}
