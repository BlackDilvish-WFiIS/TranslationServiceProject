package com.project.cisco.database.repository;

import com.project.cisco.database.entity.Language;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LanguageRepository extends CrudRepository<Language, Long> {
    List<Language> findAll();
}
