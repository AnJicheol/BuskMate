package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.exception.UnauthorizedPostAccessException;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityPostDeleteFacadeService {
    private final CommunityPostRepository communityPostRepository;
    private final CommunityPostService postService;
    private final CommunityCommentService commentService;

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
