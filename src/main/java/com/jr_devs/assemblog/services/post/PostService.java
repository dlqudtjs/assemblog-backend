package com.jr_devs.assemblog.services.post;

import com.jr_devs.assemblog.models.PostDto;
import com.jr_devs.assemblog.models.ResponseDto;

public interface PostService {

    ResponseDto createPost(PostDto postDto);

    ResponseDto tempSavePost(PostDto postDto);

    ResponseDto updatePost(PostDto postDto);

    ResponseDto deletePost(Long postId);
}
