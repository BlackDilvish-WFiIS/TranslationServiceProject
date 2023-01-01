package com.project.cisco.unit.service;

import com.project.cisco.database.entity.Language;
import com.project.cisco.database.repository.LanguageRepository;
import com.project.cisco.dto.LanguageDto;
import com.project.cisco.mapper.LanguageMapper;
import com.project.cisco.service.impl.LanguageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class LanguageServiceTest {
    @InjectMocks
    private LanguageServiceImpl languageService;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private LanguageMapper languageMapper;

    private Language language;
    private Language modifiedLanguage;
    private LanguageDto languageDto;
    private LanguageDto modifiedLanguageDto;

    @BeforeEach
    public void init(){
        language = new Language();
        language.setLanguage("Latin");
        language.setId(1L);

        modifiedLanguage = new Language();
        modifiedLanguage.setLanguage("German");
        modifiedLanguage.setId(1L);

        languageDto = LanguageDto.builder().id(1L).language("Latin").build();
        modifiedLanguageDto = LanguageDto.builder().id(1L).language("German").build();
    }

    @Test
    public void shouldAddNewLanguage(){
        Mockito.when(languageMapper.map(languageDto)).thenReturn(language);

        languageService.addLanguage(languageDto);

        Mockito.verify(languageRepository, Mockito.times(1)).save(language);
    }

    @Test
    public void shouldGetExistingLanguage(){
        Mockito.when(languageRepository.findById(language.getId())).thenReturn(Optional.of(language));

        languageService.getLanguage(1L);

        Mockito.verify(languageRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void shouldModifyExistingLanguage(){
        Mockito.when(languageRepository.findById(language.getId())).thenReturn(Optional.of(language));

        languageService.modifyLanguage(modifiedLanguageDto);

        Mockito.verify(languageRepository, Mockito.times(1)).save(modifiedLanguage);
    }

    @Test
    public void shouldDeleteExistingLanguage(){
        Mockito.when(languageRepository.findById(language.getId())).thenReturn(Optional.of(language));

        languageService.deleteLanguage(language.getId());

        Mockito.verify(languageRepository, Mockito.times(1)).deleteById(language.getId());
    }
}
