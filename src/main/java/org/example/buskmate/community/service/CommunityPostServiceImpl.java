package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.crud.request.CreatePostRequest;
import org.example.buskmate.community.dto.crud.request.DeletePostRequest;
import org.example.buskmate.community.dto.crud.request.UpdatePostRequest;
import org.example.buskmate.community.dto.crud.response.DeletePostResponse;
import org.example.buskmate.community.dto.crud.response.PostIdResponse;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityPostRepository communityPostRepo;

    @Transactional
    public void createPost(CreatePostRequest request){
        CommunityPost post = CommunityPost.createPost(
                request.title(),
                request.authorId(),
                request.content()
        );
        communityPostRepo.save(post);
    }

    public PostIdResponse getPostById(String id){
        return null;
    }

    @Transactional
    public void updatePost(Integer id, UpdatePostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        post.updatePost(request.title(), request.content());
        communityPostRepo.save(post);
    }

    public DeletePostResponse deletePost(DeletePostRequest request){
        return null;
    }
}
