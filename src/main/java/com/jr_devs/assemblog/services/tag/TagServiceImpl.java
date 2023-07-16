package com.jr_devs.assemblog.services.tag;

import com.jr_devs.assemblog.models.dtos.ResponseDto;
import com.jr_devs.assemblog.models.Tag;
import com.jr_devs.assemblog.models.dtos.TagDto;
import com.jr_devs.assemblog.repositoryes.JpaTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final JpaTagRepository jpaTagRepository;

    @Override
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
