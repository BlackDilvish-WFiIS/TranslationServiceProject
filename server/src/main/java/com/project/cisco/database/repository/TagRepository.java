package com.project.cisco.database.repository;

import com.project.cisco.database.entity.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAll();
}