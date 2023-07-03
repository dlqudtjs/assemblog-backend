package com.jr_devs.assemblog.services.tag;

import com.jr_devs.assemblog.models.ResponseDto;
import com.jr_devs.assemblog.models.Tag;
import com.jr_devs.assemblog.models.TagDto;

import java.util.List;

public interface TagService {

    ResponseDto createTag(TagDto tagDto);

    ResponseDto deleteTag(Long tagId);

    List<Tag> readAllTags();
}
