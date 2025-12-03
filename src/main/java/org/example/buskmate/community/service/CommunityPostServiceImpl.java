package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityComment;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.domain.DeleteStatus;
import org.example.buskmate.community.dto.crud.request.*;
import org.example.buskmate.community.dto.crud.response.ReadAllPostResponse;
import org.example.buskmate.community.dto.crud.response.ReadPostResponse;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityPostRepository communityPostRepo;
    private final CommunityComment communityComment;

    // 1. 게시글 생성
    @Transactional
    public void createPost(CreatePostRequest request){
        CommunityPost post = CommunityPost.createPost(
                request.title(),
                request.authorId(),
                request.content()
        );
        communityPostRepo.save(post);
    }
    // 2. 전체 게시글 조회
    public Page<ReadAllPostResponse> getAllPost(ReadAllPostRequest request){
        Sort sort = request.desc() ? Sort.by(request.sortBy()).descending()
                                    : Sort.by(request.sortBy()).ascending();
        Pageable pageable = PageRequest.of(request.page(), request.size(), sort);

        return communityPostRepo.findByIsDeleted(DeleteStatus.ACTIVE, pageable)
                .map(post -> {
                    long commentCount = commentRepository.countByCoummunityPostId(post.getId());
                    return new ReadAllPostResponse(
                            post.getAuthorId(),
                            post.getTitle(),
                            post.getViewCount()
                    );
                });
    }
    // 3. 특정 게시글 조회
    public ReadPostResponse getPostId(Integer id, ReadPostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
//        post.setViewCount(post.getViewCount() + 1);
        return ReadPostResponse.of(post);
    }
    // 4. 게시글 수정
    @Transactional
    public void updatePost(Integer id, UpdatePostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        post.updatePost(request.title(), request.content());
        communityPostRepo.save(post);
    }

    // 5. 게시글 삭제
    public void deletePost(Integer id, DeletePostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        post.softDelete();
    }
}

// 댓글 수, 조회 수