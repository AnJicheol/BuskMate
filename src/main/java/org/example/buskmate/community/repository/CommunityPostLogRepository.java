package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityPostLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 게시글 조회 로그 JPA 레포지토리
 * - 특정 사용자의 최근 조회 로그 확인 및 게시글 조회수 집계를 제공한다.
 */
@Repository
public interface CommunityPostLogRepository extends JpaRepository<CommunityPostLog, Long> {

    /**
     * 특정 게시글에 대해 특정 사용자의 가장 최근 조회 로그를 조회한다.
     */
    Optional<CommunityPostLog> findTop1ByCommunityPostIdAndViewerIdOrderByViewedAtDesc(
            Long postId,
            String viewerId
    );

    /**
     * 특정 게시글의 고유 조회자 수(중복 제거)를 조회한다.
     */
    @Query("""
        select coalesce(count(distinct log.viewerId), 0L)
        from CommunityPostLog log
        where log.communityPost.id = :postId
    """)
    Long countByCommunityPostId(@Param("postId") Long postId);
}