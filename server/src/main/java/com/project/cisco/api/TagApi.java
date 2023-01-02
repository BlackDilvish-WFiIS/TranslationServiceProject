package com.project.cisco.api;

import com.project.cisco.dto.TagDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface TagApi {
    @GetMapping(value = "/tag/{id}")
    ResponseEntity<TagDto> getTag(@PathVariable(value = "id") Long tagId);

    @GetMapping(value = "/tag")
    ResponseEntity<List<TagDto>> getTags();

    @PostMapping(value = "/tag")
    ResponseEntity<TagDto> addTag(@RequestBody TagDto tagDto);

    @DeleteMapping(value = "/tag/{id}")
    ResponseEntity<Void> deleteTag(@PathVariable(value = "id") Long tagId);

    @PutMapping(value = "/tag")
    ResponseEntity<TagDto> modifyTag(@RequestBody TagDto tagDto);
}