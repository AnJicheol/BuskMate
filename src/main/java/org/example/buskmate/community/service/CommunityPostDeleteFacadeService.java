package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.exception.UnauthorizedPostAccessException;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 삭제 파사드 서비스
 * - 게시글 삭제 시 댓글 소프트 삭제까지 함께 처리한다.
 */
@Service
@RequiredArgsConstructor
public class CommunityPostDeleteFacadeService {

    private final CommunityPostRepository communityPostRepository;
    private final CommunityPostService postService;
    private final CommunityCommentService commentService;

    /**
     * 게시글 삭제와 댓글 소프트 삭제를 하나의 트랜잭션으로 묶어 처리한다.
     * - 작성자 검증 후 댓글 소프트 삭제 -> 게시글 소프트 삭제를 수행한다.
     */
    @Transactional
    public void deletePostWithComment(String authorId, Long postId){

        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 입니다."));

        if(!post.getAuthorId().equals(authorId)) {
            throw new UnauthorizedPostAccessException("작성자만 삭제할 수 있습니다.");
        }

        commentService.softDeleteCommentsByPostId(postId);
        postService.deletePost(authorId, postId);
    }
}
