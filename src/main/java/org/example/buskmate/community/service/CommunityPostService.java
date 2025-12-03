package org.example.buskmate.community.service;

import org.example.buskmate.community.dto.crud.request.CreatePostRequest;
import org.example.buskmate.community.dto.crud.request.DeletePostRequest;
import org.example.buskmate.community.dto.crud.request.UpdatePostRequest;
import org.example.buskmate.community.dto.crud.response.DeletePostResponse;
import org.example.buskmate.community.dto.crud.response.PostIdResponse;

public interface CommunityPostService {

    void createPost(CreatePostRequest request);

    PostIdResponse getPostById(String id);

    void updatePost(Integer id, UpdatePostRequest request);

    DeletePostResponse deletePost(DeletePostRequest request);
}
