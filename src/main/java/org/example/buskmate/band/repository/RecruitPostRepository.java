package org.example.buskmate.band.repository;

import org.example.buskmate.band.domain.RecruitPost;
import org.example.buskmate.band.domain.RecruitPostStatus;
import org.example.buskmate.band.dto.recruitpost.RecruitPostDetailResponseDto;
import org.example.buskmate.band.dto.recruitpost.RecruitPostListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface RecruitPostRepository extends JpaRepository<RecruitPost, Long> {

    Optional<RecruitPost> findByPostId(String postId);

    List<RecruitPost> findAllByBandId(String bandId);

    @Query("""
        select new org.example.buskmate.band.dto.recruitpost.RecruitPostDetailResponseDto(
            rp.postId,
            rp.band.bandId,
            rp.title,
            rp.content,
            rp.status,
            rp.createdAt
        )
        from RecruitPost rp
        where rp.postId = :postId
    """)
    Optional<RecruitPostDetailResponseDto> findDetail(@Param("postId") String postId);

    @Query("""
      select new org.example.buskmate.band.dto.recruitpost.RecruitPostListDto(
        rp.postId,
        rp.band.bandId,
        rp.title,
        rp.status,
        rp.createdAt
      )
      from RecruitPost rp
      where rp.status = :status
      order by rp.createdAt desc
""")
    List<RecruitPostListDto> findAllByStatus(@Param("status") RecruitPostStatus status);

}
