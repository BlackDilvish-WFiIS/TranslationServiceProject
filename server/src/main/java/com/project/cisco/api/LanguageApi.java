package com.project.cisco.api;

import com.project.cisco.dto.LanguageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface LanguageApi {
    @GetMapping(value = "/language/{id}")
    ResponseEntity<LanguageDto> getLanguage(@PathVariable(value = "id") Long languageId);

    @PostMapping(value = "/language")
    ResponseEntity<LanguageDto> addLanguage(@RequestBody LanguageDto languageDto);

    @DeleteMapping(value = "/language/{id}")
    ResponseEntity<Void> deleteLanguage(@PathVariable(value = "id") Long languageId);

    @PutMapping(value = "/language")
    ResponseEntity<LanguageDto> modifyLanguage(@RequestBody LanguageDto languageDto);
}
