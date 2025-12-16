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
/**
 * 모집 글(Recruit Post) 엔티티에 대한
 * 데이터 접근을 담당하는 Repository 인터페이스입니다.
 *
 * <p>
 * {@code RecruitPostRepository}는 모집 글 엔티티 자체 조회뿐만 아니라,
 * 조회 성능과 책임 분리를 위해
 * DTO 기반 조회(Query Projection) 기능을 함께 제공합니다.
 * </p>
 *
 * <h3>설계 특징</h3>
 * <ul>
 *   <li>엔티티 조회: 수정/상태 변경을 위한 내부 처리용</li>
 *   <li>DTO 조회: 목록/상세 조회 API 응답 전용</li>
 *   <li>N+1 문제 방지를 위한 JPQL 기반 프로젝션 사용</li>
 * </ul>
 */
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
