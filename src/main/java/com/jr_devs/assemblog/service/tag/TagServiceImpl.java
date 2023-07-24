package com.jr_devs.assemblog.service.tag;

import com.jr_devs.assemblog.model.dto.ResponseDto;
import com.jr_devs.assemblog.model.tag.Tag;
import com.jr_devs.assemblog.model.tag.TagDto;
import com.jr_devs.assemblog.repository.JpaTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final JpaTagRepository jpaTagRepository;

    @Override
    @Transactional
    public Tag createTag(TagDto tagDto) {
        Tag tag = jpaTagRepository.findByName(tagDto.getName());

        if (tag != null) {
            return tag;
        }

        return jpaTagRepository.save(Tag.builder()
                .name(tagDto.getName())
                .build());
    }

    @Override
    public Tag readTagById(Long tagId) {
        return jpaTagRepository.findById(tagId).orElse(null);
    }

    @Override
    public Tag readTagByName(String name) {
        return jpaTagRepository.findByName(name);
    }

    @Override
    public ResponseDto deleteTag(Long tagId) {
        jpaTagRepository.deleteById(tagId);

        return createResponse(HttpStatus.OK.value(), "Success delete tag");
    }

    @Override
    public List<Tag> readAllTags() {
        return jpaTagRepository.findAllTags();
    }

    private ResponseDto createResponse(int statusCode, String message) {
        return ResponseDto.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
