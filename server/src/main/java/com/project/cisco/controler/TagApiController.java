package com.project.cisco.controler;

import com.project.cisco.api.TagApi;
import com.project.cisco.dto.TagDto;
import com.project.cisco.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = { "http://localhost:3000/" },
        maxAge = 900
)
@RestController
@RequestMapping("/v1")
public class TagApiController implements TagApi {
    @Autowired
    private TagService tagService;

    @Override
    public ResponseEntity<TagDto> getTag(Long tagId) {
        TagDto tagDto = tagService.getTag(tagId);
        return ResponseEntity.ok(tagDto);
    }

    @Override
    public ResponseEntity<List<TagDto>> getTags() {
        List<TagDto> tagDtos = tagService.getTags();
        return ResponseEntity.ok(tagDtos);
    }

    @Override
    public ResponseEntity<TagDto> addTag(TagDto tagDto) {
        TagDto savedTagDto = tagService.addTag(tagDto);
        return ResponseEntity.ok(savedTagDto);
    }

    @Override
    public ResponseEntity<Void> deleteTag(Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TagDto> modifyTag(TagDto tagDto) {
        TagDto modifiedTagDto = tagService.modifyTag(tagDto);
        return ResponseEntity.ok(modifiedTagDto);
    }
}
