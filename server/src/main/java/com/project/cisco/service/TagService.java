package com.project.cisco.service;

import com.project.cisco.dto.TagDto;
import java.util.List;

public interface TagService {
    TagDto addTag(TagDto tagDto);

    TagDto getTag(Long id);

    List<TagDto> getTags();

    void deleteTag(Long id);

    TagDto modifyTag(TagDto tagDto);
}
