package com.jr_devs.assemblog.services.tag;

import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.models.Tag;
import com.jr_devs.assemblog.models.TagDto;
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
    public ResponseDto createTag(TagDto tagDto) {
        Tag.builder()
                .name(tagDto.getName())
                .build();

        return createResponse(HttpStatus.OK.value(), "Success create tag");
    }

    @Override
    public ResponseDto updateTag(TagDto tagDto) {
        Tag tag = jpaTagRepository.findByName(tagDto.getName());

        if (tag != null) {
            return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate tag name");
        }

        tag.setName(tagDto.getName());

        return createResponse(HttpStatus.OK.value(), "Success update tag");
    }

    @Override
    public ResponseDto deleteTag(Long tagId) {
        jpaTagRepository.deleteById(tagId);

        return createResponse(HttpStatus.OK.value(), "Success delete tag");
    }

    @Override
    public List<Tag> readAllTags() {
        return jpaTagRepository.findAllByOrderByNameAsc();
    }

    private ResponseDto createResponse(int statusCode, String message) {
        return ResponseDto.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
