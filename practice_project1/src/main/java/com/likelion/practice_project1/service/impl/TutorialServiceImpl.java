package com.likelion.practice_project1.service.impl;

import com.likelion.practice_project1.entity.Tutorial;
import com.likelion.practice_project1.repository.TutorialRepository;
import com.likelion.practice_project1.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialServiceImpl implements TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Override
    public List<Tutorial> findAll() {
        return tutorialRepository.findAll();
    }

    @Override
    public Tutorial findById(Long id) {
        return tutorialRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAll() {
        tutorialRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        tutorialRepository.deleteById(id);
    }

    @Override
    public List<Tutorial> findByPublished() {
        return tutorialRepository.findByPublished(true);
    }

    @Override
    public List<Tutorial> findByTitleContain(String keyword) {
        return tutorialRepository.findByTitleContaining(keyword);
    }

    @Override
    public Tutorial save(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }
}
