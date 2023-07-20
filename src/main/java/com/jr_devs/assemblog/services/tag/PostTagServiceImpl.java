package com.jr_devs.assemblog.services.tag;


import com.jr_devs.assemblog.models.post.PostTag;
import com.jr_devs.assemblog.repositoryes.JpaPostTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostTagServiceImpl implements PostTagService {

    private final JpaPostTagRepository postTagRepository;

    @Override
    public void createPostTag(Long boardId, Long tagId) {
        PostTag postTag = postTagRepository.findByPostIdAndTagId(boardId, tagId);

        if (postTag != null) {
            return;
        }

        postTagRepository.save(PostTag.builder()
                .postId(boardId)
                .tagId(tagId)
                .build());
    }

    @Override
    public void deletePostTag(Long postId, Long tagId) {
        PostTag postTag = postTagRepository.findByPostIdAndTagId(postId, tagId);

        if (postTag == null) {
            return;
        }

        postTagRepository.deleteById(postTag.getId());
    }

    @Override
    public List<PostTag> getPostTagsByPostId(Long postId) {
        return postTagRepository.findAllByPostId(postId);
    }

    @Override
    public List<PostTag> getPostTagsByTagId(Long tagId) {
        return postTagRepository.findAllByTagId(tagId);
    }
}
