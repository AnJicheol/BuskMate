package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.PostStatus;
import org.example.buskmate.community.dto.post.crud.request.*;
import org.example.buskmate.community.dto.post.crud.response.CommunityPostReadAllPostResponse;
import org.example.buskmate.community.exception.UnauthorizedPostAccessException;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 서비스 구현체
 * - 게시글 생성/목록 조회/수정/삭제(소프트) 로직을 처리한다.
 */
@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityPostRepository communityPostRepo;

    /**
     * 게시글 엔티티를 생성하여 저장한다.
     */
    @Transactional
    public void createPost(CommunityPostCreatePostRequest request, String authorId){
        CommunityPost post = CommunityPost.createPost(
                request.title(),
                authorId,
                request.content()
        );

        communityPostRepo.save(post);
    }

    /**
     * 활성 게시글 목록을 페이징으로 조회한다.
     */
    @Transactional(readOnly = true)
    public Page<CommunityPostReadAllPostResponse> getAllPost(
            Pageable pageable,
            String authorId
    ){
        return communityPostRepo.findAllPostSummary(PostStatus.ACTIVE, pageable);
    }

    /**
     * 게시글 제목/본문을 수정한다. (작성자 검증 포함)
     */
    @Transactional
    public void updatePost(String authorId, Long postId, CommunityPostUpdatePostRequest request){
        communityPostRepo.findById(postId)
                .ifPresentOrElse(post -> {
                    if(!post.getAuthorId().equals(authorId)){
                        throw new UnauthorizedPostAccessException("작성자만 수정할 수 있습니다.");
                    }
                    post.updatePost(request.title(), request.content());
                    communityPostRepo.save(post);
                }, () -> {
                    throw new IllegalArgumentException("존재하지 않는 게시글 입니다.");
                });
    }

    /**
     * 게시글을 소프트 삭제한다. (작성자 검증 포함)
     */
    @Transactional
    public void deletePost(String authorId, Long id){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        if(!post.getAuthorId().equals(authorId)){
            throw new UnauthorizedPostAccessException("작성자만 삭제할 수 있습니다.");
        }

        post.softDelete();
    }
}
