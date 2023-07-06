package com.jr_devs.assemblog.services.post;

import com.jr_devs.assemblog.models.dtos.PostDto;
import com.jr_devs.assemblog.models.dtos.PostResponseDto;
import com.jr_devs.assemblog.models.dtos.ResponseDto;

public interface PostService {

    ResponseDto createPost(PostDto postDto);

    ResponseDto tempSavePost(PostDto postDto);

    PostResponseDto readPost(Long postId);

    ResponseDto updatePost(PostDto postDto);

    ResponseDto deletePost(Long postId);
}
