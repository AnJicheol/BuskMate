package org.example.buskmate.community.repository;

import org.example.buskmate.community.domain.CommunityComment;
import org.example.buskmate.community.domain.DeleteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {

    // 특정 게시글의 활성댓글만 시간순으로 조회하기
    List<CommunityComment> findByCommunityPostIdAndIsDeletedOrderByCreatedAtAsc(
            Long communityPostId,
            DeleteStatus isDeleted
    );

    // Post당 댓글 수 조회
    Long countByCommunityPostIdAndIsDeleted(Long postId, DeleteStatus isDeleted);
}
