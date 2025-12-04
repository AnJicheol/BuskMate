package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityPostDataHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPostDataHistoryRepository extends JpaRepository<CommunityPostDataHistory, Long> {

    // 특정 게시글의 히스토리 목록
    List<CommunityPostDataHistory> findByCommunityPostIdOrderByCreatedAtDesc(Long communityPostId);


}
