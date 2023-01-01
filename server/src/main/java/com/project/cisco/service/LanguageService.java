package com.project.cisco.service;

import com.project.cisco.dto.LanguageDto;

import java.util.List;

public interface LanguageService {
    LanguageDto addLanguage(LanguageDto languageDto);

    LanguageDto getLanguage(Long id);

    List<LanguageDto> getLanguages();

    void deleteLanguage(Long id);

    LanguageDto modifyLanguage(LanguageDto languageDto);
}
