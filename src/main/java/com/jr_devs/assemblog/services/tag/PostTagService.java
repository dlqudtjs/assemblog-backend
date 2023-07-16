package com.jr_devs.assemblog.services.tag;

import com.jr_devs.assemblog.models.PostTag;

import java.util.List;
import java.util.Optional;

public interface PostTagService {

    void createPostTag(Long boardId, Long tagId);

    void deletePostTag(Long postId, Long tagId);

    List<PostTag> getPostTagsByPostId(Long postId);

    List<PostTag> getPostTagsByTagId(Long tagId);
}
