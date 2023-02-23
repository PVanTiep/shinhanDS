package com.likelion.practice_project1.repository;

import com.likelion.practice_project1.entity.Tutorial;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class TutorialRepositoryTest {

    @Mock
    TutorialRepository tutorialRepository;

//    @BeforeEach
//    void addTutorial() {
//        when(tutorialRepository.findById(1L)).thenReturn(Optional.of(new Tutorial(1L, null, null, false)));
//    }

    @AfterEach
    void tearDown() {
        tutorialRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Tutorial> tutorialList = new ArrayList<>();
        Tutorial tutorial1 = new Tutorial(1L, "title1", "description1", false);
        Tutorial tutorial2 = new Tutorial(2L, "title2", "description2", false);
        Tutorial tutorial3 = new Tutorial(3L, "title3", "description3", false);
        tutorialList.add(tutorial1);
        tutorialList.add(tutorial2);
        tutorialList.add(tutorial3);
        when(tutorialRepository.findAll()).thenReturn(tutorialList);

        List<Tutorial> newTutorialList = tutorialRepository.findAll();
        Assertions.assertFalse(newTutorialList.isEmpty());
        Assertions.assertTrue(newTutorialList.size() == 3);
    }

    @Test
    void deleteAll() {
        doNothing().when(tutorialRepository).deleteAll();
        tutorialRepository.deleteAll();
        verify(tutorialRepository, times(1)).deleteAll();
    }

    @Test
    void findById() {
        when(tutorialRepository.findById(1L)).thenReturn(Optional.of(new Tutorial(1L, "title1", "des1", false)));
        Tutorial tutorial = tutorialRepository.findById(1L).get();
        Assertions.assertNotNull(tutorial);
    }

    @Test
    void deleteById() {
        doNothing().when(tutorialRepository).deleteById(2L);
        tutorialRepository.deleteById(2L);
        verify(tutorialRepository, times(1)).deleteById(2L);
    }

    @Test
    void findByPublished() {
        List<Tutorial> tutorialList = new ArrayList<>();
        when(tutorialRepository.findByPublished(true)).thenReturn(tutorialList);

        //Case 1
        Assertions.assertTrue(tutorialRepository.findByPublished(true).isEmpty());

        //Case 2
        Tutorial tutorial1 = new Tutorial(1L, "title1", "description1", true);
        Tutorial tutorial2 = new Tutorial(2L, "title2", "description2", true);
        tutorialList.add(tutorial1);
        tutorialList.add(tutorial2);

        List<Tutorial> result = tutorialRepository.findByPublished(true);
        result.stream().forEach(i -> {
            Assertions.assertTrue(i.getPublished() == true);
        });

    }

    @Test
    void findByTitleContaining() {
        List<Tutorial> tutorialList = new ArrayList<>();
        when(tutorialRepository.findByTitleContaining("title1")).thenReturn(tutorialList);

        //Case 1
        Assertions.assertTrue(tutorialRepository.findByTitleContaining("title1").isEmpty());

        //Case 2
        Tutorial tutorial1 = new Tutorial(1L, "title12", "description1", true);
        Tutorial tutorial2 = new Tutorial(2L, "title122", "description2", true);
        tutorialList.add(tutorial1);
        tutorialList.add(tutorial2);

        List<Tutorial> result = tutorialRepository.findByTitleContaining("title1");
        Assertions.assertTrue(result.size() == 2);
        result.stream().forEach(i -> {
            Assertions.assertTrue(i.getTitle().contains("title1"));
        });
    }

    @Test
    void save() {
        Tutorial tutorial = new Tutorial(1L, "title1", "des1", false);

        when(tutorialRepository.save(tutorial)).thenReturn(tutorial);

        Tutorial tutorial1 = tutorialRepository.save(tutorial);

        Assertions.assertNotNull(tutorial1);
        Assertions.assertTrue(tutorial1.getId().equals(tutorial.getId()));
        Assertions.assertTrue(tutorial1.getTitle().equals(tutorial.getTitle()));
        Assertions.assertTrue(tutorial1.getDescription().equals(tutorial.getDescription()));
        Assertions.assertTrue(tutorial1.getPublished().equals(tutorial.getPublished()));
    }
}