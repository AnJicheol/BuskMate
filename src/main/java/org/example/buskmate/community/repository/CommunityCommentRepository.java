package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityComment;
import org.example.buskmate.community.domain.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 댓글 JPA 레포지토리
 * - 게시글별 활성 댓글 조회 쿼리를 제공한다.
 */
@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {

    /**
     * 특정 게시글의 활성 댓글을 생성 시간 오름차순으로 조회한다.
     */
    List<CommunityComment> findByCommunityPostIdAndIsActiveOrderByCreatedAtAsc(
            Long communityPostId,
            PostStatus isActive
    );
}
