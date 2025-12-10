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

@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityPostRepository communityPostRepo;

    // 1. 게시글 생성
    @Transactional
    public void createPost(CommunityPostCreatePostRequest request, String authorId){
        CommunityPost post = CommunityPost.createPost(
                request.title(),
                authorId,
                request.content()
        );

        communityPostRepo.save(post);
    }
    // 2. 전체 게시글 조회
    @Transactional(readOnly = true)
    public Page<CommunityPostReadAllPostResponse> getAllPost(
            Pageable pageable,
            String authorId
    ){
        return communityPostRepo.findAllPostSummary(PostStatus.ACTIVE, pageable);
    }
    // 3. 게시글 조회는 CommunityPostFacadeService
    // 4. 게시글 수정
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

    // 5. 게시글 삭제
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