package com.project.cisco.service.impl;

import com.project.cisco.database.entity.Language;
import com.project.cisco.database.repository.LanguageRepository;
import com.project.cisco.dto.LanguageDto;
import com.project.cisco.exception.NotFoundException;
import com.project.cisco.exception.UniqueConstraintViolationException;
import com.project.cisco.mapper.LanguageMapper;
import com.project.cisco.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private LanguageMapper languageMapper;

    @Override
    public LanguageDto addLanguage(LanguageDto languageDto) {
        Language language = languageMapper.map(languageDto);
        try {
            Language savedLanguage = languageRepository.save(language);
            return languageMapper.map(savedLanguage);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintViolationException("Language with given language name already exists.");
        }
    }

    @Override
    public LanguageDto getLanguage(Long id) {
        Optional<Language> languageOptional = languageRepository.findById(id);
        if (languageOptional.isEmpty()) {
            throw new NotFoundException("Language with given id does not exist");
        }
        return languageMapper.map(languageOptional.get());
    }

    @Override
    public List<LanguageDto> getLanguages() {
        List<Language> languages = languageRepository.findAll();
        return languages.stream().map(language -> languageMapper.map(language)).toList();
    }

    @Override
    public void deleteLanguage(Long id) {
        Optional<Language> languageOptional = languageRepository.findById(id);
        if (languageOptional.isEmpty()) {
            throw new NotFoundException("Language with given id does not exist");
        }
        languageRepository.deleteById(id);
    }

    @Override
    public LanguageDto modifyLanguage(LanguageDto languageDto) {
        Optional<Language> languageOptional = languageRepository.findById(languageDto.getId());
        if (languageOptional.isEmpty()) {
            throw new NotFoundException("Language with given id does not exist");
        }
        Language language = languageOptional.get();
        language.setLanguage(languageDto.getLanguage());
        Language modifiedLanguage = languageRepository.save(language);
        return languageMapper.map(modifiedLanguage);
    }
}
