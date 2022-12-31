package com.project.cisco.mapper;

import com.project.cisco.database.entity.Language;
import com.project.cisco.dto.LanguageDto;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapper {
    public Language map(LanguageDto languageDto) {
        return new Language(languageDto.getId(), languageDto.getLanguage());
    }

    public LanguageDto map(Language language) {
        return LanguageDto.builder().id(language.getId()).language(language.getLanguage()).build();
    }
}