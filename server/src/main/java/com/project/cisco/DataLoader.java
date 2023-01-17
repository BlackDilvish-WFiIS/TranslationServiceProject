package com.project.cisco;

import com.project.cisco.database.entity.Language;
import com.project.cisco.database.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private LanguageRepository languageRepository;

    public void run(ApplicationArguments args) {
        languageRepository.save(new Language("English"));
    }
}