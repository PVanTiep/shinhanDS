package com.likelion.practice_project1.service;

import com.likelion.practice_project1.entity.Tutorial;
import com.likelion.practice_project1.repository.TutorialRepository;
import com.likelion.practice_project1.service.impl.TutorialServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class TutorialServiceTest {

    @Mock
    TutorialRepository tutorialRepository;

    @InjectMocks
    TutorialServiceImpl tutorialService;

    @BeforeEach
    void setUp() {
        List<Tutorial> tutorialList = new ArrayList<>();
        Tutorial tutorial1 = new Tutorial(1L, "title1", "description1", false);
        Tutorial tutorial2 = new Tutorial(2L, "title2", "description2", false);
        Tutorial tutorial3 = new Tutorial(3L, "title3", "description3", false);
        tutorialList.add(tutorial1);
        tutorialList.add(tutorial2);
        tutorialList.add(tutorial3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        List<Tutorial> tutorialList = new ArrayList<>();
        when(tutorialRepository.findAll()).thenReturn(tutorialList);

        //Case 1
        Assertions.assertTrue(tutorialService.findAll().isEmpty());

        //Case 2
        Tutorial tutorial1 = new Tutorial(1L, "title1", "description1", false);
        Tutorial tutorial2 = new Tutorial(2L, "title2", "description2", false);
        tutorialList.add(tutorial1);
        tutorialList.add(tutorial2);

        List<Tutorial> tutorials = tutorialService.findAll();

        Assertions.assertFalse(tutorials.isEmpty());
        Assertions.assertTrue(tutorials.size() == 2);
    }

    @Test
    void findById() {
        Tutorial tutorial = new Tutorial(1L, "title1", "des1", false);
        when(tutorialRepository.findById(1L)).thenReturn(Optional.of(tutorial));

        Tutorial result = tutorialService.findById(1L);

        Assertions.assertTrue(result.getId().equals(tutorial.getId()));
    }

    @Test
    void deleteAll() {
        doNothing().when(tutorialRepository).deleteAll();
        tutorialService.deleteAll();
        verify(tutorialRepository, times(1)).deleteAll();
    }

    @Test
    void deleteById() {
        doNothing().when(tutorialRepository).deleteById(1L);
        tutorialService.deleteById(1L);
        verify(tutorialRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByPublished() {
        List<Tutorial> tutorialList = new ArrayList<>();
        when(tutorialRepository.findByPublished(true)).thenReturn(tutorialList);

        //Case 1
        Assertions.assertTrue(tutorialService.findByPublished().isEmpty());

        //Case 2
        Tutorial tutorial1 = new Tutorial(1L, "title1", "description1", true);
        Tutorial tutorial2 = new Tutorial(2L, "title2", "description2", true);
        tutorialList.add(tutorial1);
        tutorialList.add(tutorial2);

        List<Tutorial> result = tutorialService.findByPublished();
        result.stream().forEach(i -> {
            Assertions.assertTrue(i.getPublished() == true);
        });
    }

    @Test
    void findByTitleContain() {
        List<Tutorial> tutorialList = new ArrayList<>();
        when(tutorialRepository.findByTitleContaining("title1")).thenReturn(tutorialList);

        //Case 1
        Assertions.assertTrue(tutorialService.findByTitleContain("title1").isEmpty());

        //Case 2
        Tutorial tutorial1 = new Tutorial(1L, "title12", "description1", true);
        Tutorial tutorial2 = new Tutorial(2L, "title122", "description2", true);
        tutorialList.add(tutorial1);
        tutorialList.add(tutorial2);

        List<Tutorial> result = tutorialService.findByTitleContain("title1");
        Assertions.assertTrue(result.size() == 2);
        result.stream().forEach(i -> {
            Assertions.assertTrue(i.getTitle().contains("title1"));
        });
    }

    @Test
    void save() {
        Tutorial tutorial = new Tutorial(1L, "title1", "des1", false);

        when(tutorialRepository.save(tutorial)).thenReturn(tutorial);

        Tutorial tutorial1 = tutorialService.save(tutorial);

        Assertions.assertNotNull(tutorial1);
        Assertions.assertTrue(tutorial1.getId().equals(tutorial.getId()));
        Assertions.assertTrue(tutorial1.getTitle().equals(tutorial.getTitle()));
        Assertions.assertTrue(tutorial1.getDescription().equals(tutorial.getDescription()));
        Assertions.assertTrue(tutorial1.getPublished().equals(tutorial.getPublished()));
    }
}