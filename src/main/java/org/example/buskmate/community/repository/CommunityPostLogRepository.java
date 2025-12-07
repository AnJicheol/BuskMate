package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityPostLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityPostLogRepository extends JpaRepository<CommunityPostLog, Long> {


    CommunityPostLog findTop1ByCommunityPostIdAndViewerIdOrderByViewedAtDesc(
            Long postId,
            String viewerId
    );

    Long countByCommunityPostId(Long postId);
}
