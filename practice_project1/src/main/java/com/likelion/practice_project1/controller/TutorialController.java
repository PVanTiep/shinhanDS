package com.likelion.practice_project1.controller;

import com.likelion.practice_project1.entity.Tutorial;
import com.likelion.practice_project1.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getListTutorials(@RequestParam(required = false) String title) {
        try {
            if(title != null) {
                List<Tutorial> tutorialList = tutorialService.findByTitleContain(title);
                if(tutorialList.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity(tutorialList, HttpStatus.OK);
                }
            } else {
                List<Tutorial> tutorialList = tutorialService.findAll();
                if(tutorialList.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity(tutorialList, HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            return new ResponseEntity<>(tutorialService.save(tutorial), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable Long id) {
        try {
            Tutorial tutorial = tutorialService.findById(id);
            if(tutorial != null) {
                return new ResponseEntity<>(tutorial, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@RequestBody Tutorial tutorial, @PathVariable Long id) {
        try {
            Tutorial oldTutorial = tutorialService.findById(id);
            if(oldTutorial == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                oldTutorial.setTitle(tutorial.getTitle());
                oldTutorial.setDescription(tutorial.getDescription());
                oldTutorial.setPublished(tutorial.getPublished());
                return new ResponseEntity<>(tutorialService.save(oldTutorial), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity deleteTutorial(@PathVariable Long id) {
        try {
            tutorialService.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity deleteAllTutorials() {
        try {
            tutorialService.deleteAll();
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> getTutorialsPublished() {
        try {
            List<Tutorial> tutorialList = tutorialService.findByPublished();
            if(tutorialList.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(tutorialList, HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
