package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityPostLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CommunityPostLogRepository extends JpaRepository<CommunityPostLog, Long> {


    Optional<CommunityPostLog> findTop1ByCommunityPostIdAndViewerIdOrderByViewedAtDesc(
            Long postId,
            String viewerId
    );

    @Query("""
        select count(distinct log.viewerId)
        from CommunityPostLog log
        where log.communityPost.id = :postId
    """)
    Long countByCommunityPostId(@Param("postId") Long postId);
}
