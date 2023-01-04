package com.project.cisco.unit.mapper;

import com.project.cisco.CiscoApplication;
import com.project.cisco.database.entity.Language;
import com.project.cisco.dto.LanguageDto;
import com.project.cisco.mapper.LanguageMapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest(classes = CiscoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LanguageMapperTest {
    @Autowired
    private LanguageMapper languageMapper;

    @Test
    public void shouldConvertLanguageToLanguageDtoCorrectly() {
        Language language = new Language();
        language.setId(1L);
        language.setLanguage("Latin");

        LanguageDto expectedDto = LanguageDto.builder().id(1L).language("Latin").build();

        LanguageDto resultDto = languageMapper.map(language);

        Assertions.assertEquals(resultDto.getId(), expectedDto.getId());
        Assertions.assertEquals(resultDto.getLanguage(), expectedDto.getLanguage());
    }

    @Test
    public void shouldConvertLanguageDtoToLanguageCorrectly() {
        Language expectedLanguage = new Language();
        expectedLanguage.setId(1L);
        expectedLanguage.setLanguage("Latin");

        LanguageDto languageDto = LanguageDto.builder().id(2L).language("Latin").build();

        Language result = languageMapper.map(languageDto);

        Assertions.assertEquals(expectedLanguage.getLanguage(), result.getLanguage());
    }
}
