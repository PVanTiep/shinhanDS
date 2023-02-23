package com.likelion.practice_project1.service;

import com.likelion.practice_project1.entity.Tutorial;

import java.util.List;

public interface TutorialService {
    List<Tutorial> findAll();

    Tutorial findById(Long id);

    void deleteAll();

    void deleteById(Long id);

    List<Tutorial> findByPublished();

    List<Tutorial> findByTitleContain(String keyword);

    Tutorial save(Tutorial tutorial);
}
