package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityPostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPostDataRepository extends JpaRepository<CommunityPostData, Long> {

    // 특정 게시글(postId)의 블록들을 sortOrder 순으로 조회
    List<CommunityPostData> findByCommunityPost_IdOrderBySortOrderAsc(Long communityPostId);
}
