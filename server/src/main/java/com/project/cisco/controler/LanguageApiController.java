package com.project.cisco.controler;

import com.project.cisco.api.LanguageApi;
import com.project.cisco.dto.LanguageDto;
import com.project.cisco.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
public class LanguageApiController implements LanguageApi {
    @Autowired
    private LanguageService languageService;

    @Override
    public ResponseEntity<LanguageDto> getLanguage(Long languageId) {
        LanguageDto languageDto = languageService.getLanguage(languageId);
        return ResponseEntity.ok(languageDto);
    }

    @Override
    public ResponseEntity<LanguageDto> addLanguage(LanguageDto languageDto) {
        LanguageDto savedDto = languageService.addLanguage(languageDto);
        return ResponseEntity.ok(savedDto);
    }

    @Override
    public ResponseEntity<Void> deleteLanguage(Long languageId) {
        languageService.deleteLanguage(languageId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LanguageDto> modifyLanguage(LanguageDto languageDto) {
        LanguageDto modifiedLanguageDto = languageService.modifyLanguage(languageDto);
        return ResponseEntity.ok(modifiedLanguageDto);
    }
}
