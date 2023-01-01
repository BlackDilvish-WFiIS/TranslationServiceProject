package com.project.cisco.api;

import com.project.cisco.dto.LanguageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface LanguageApi {
    @GetMapping(value = "/language/{id}")
    ResponseEntity<LanguageDto> getLanguage(@PathVariable(value = "id") Long languageId);

    @GetMapping(value = "/language")
    ResponseEntity<List<LanguageDto>> getLanguages();

    @PostMapping(value = "/language")
    ResponseEntity<LanguageDto> addLanguage(@RequestBody LanguageDto languageDto);

    @DeleteMapping(value = "/language/{id}")
    ResponseEntity<Void> deleteLanguage(@PathVariable(value = "id") Long languageId);

    @PutMapping(value = "/language")
    ResponseEntity<LanguageDto> modifyLanguage(@RequestBody LanguageDto languageDto);
}
