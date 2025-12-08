package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityPostLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CommunityPostLogRepository extends JpaRepository<CommunityPostLog, Long> {


    Optional<CommunityPostLog> findTop1ByCommunityPostIdAndViewerIdOrderByViewedAtDesc(
            Long postId,
            String viewerId
    );

    Long countByCommunityPostId(Long postId);
}
