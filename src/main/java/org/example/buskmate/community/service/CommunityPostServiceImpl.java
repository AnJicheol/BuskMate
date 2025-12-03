package org.example.buskmate.community.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.community.domain.CommunityPost;
import org.example.buskmate.community.dto.crud.request.*;
import org.example.buskmate.community.dto.crud.response.ReadAllPostResponse;
import org.example.buskmate.community.dto.crud.response.ReadPostResponse;
import org.example.buskmate.community.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<ReadAllPostResponse> getAllPost(ReadAllPostRequest request){
        return communityPostRepo.findAll()
                .stream()
                .map(ReadAllPostResponse :: of)
                .toList();
    }

    public ReadPostResponse getPostId(Integer id, ReadPostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        return ReadPostResponse.of(post);
    }

    @Transactional
    public void updatePost(Integer id, UpdatePostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        post.updatePost(request.title(), request.content());
        communityPostRepo.save(post);
    }


    public void deletePost(Integer id, DeletePostRequest request){
        CommunityPost post = communityPostRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        post.softDelete();
    }
}
