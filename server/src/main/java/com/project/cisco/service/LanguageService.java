package com.project.cisco.service;

import com.project.cisco.dto.LanguageDto;

public interface LanguageService {
    LanguageDto addLanguage(LanguageDto languageDto);

    LanguageDto getLanguage(Long id);

    void deleteLanguage(Long id);

    LanguageDto modifyLanguage(LanguageDto languageDto);
}
