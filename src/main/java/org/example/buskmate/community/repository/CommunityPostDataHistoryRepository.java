package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityPostDataHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 게시글 히스토리 JPA 레포지토리
 * - 게시글별 히스토리 목록 조회를 제공한다.
 */
@Repository
public interface CommunityPostDataHistoryRepository extends JpaRepository<CommunityPostDataHistory, Long> {

    /**
     * 특정 게시글의 히스토리 목록을 최신순으로 조회한다.
     */
    List<CommunityPostDataHistory> findByCommunityPostIdOrderByCreatedAtDesc(Long communityPostId);


}
