package com.example.mybatisdemo.controller;

import com.example.mybatisdemo.model.Tutorial;
import com.example.mybatisdemo.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    @GetMapping("tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title){
        try {
            List<Tutorial> tutorialList = new ArrayList<Tutorial>();
            if(title == null)
                tutorialList = tutorialService.findAll();
            else
                tutorialList = tutorialService.findByTitleContaining(title);
            if (tutorialList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorialList,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id){
        Tutorial tutorial = tutorialService.findById(id);
        if (tutorial!=null)
            return new ResponseEntity<>(tutorial,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial){
        try {
            Tutorial tempTutorial = new Tutorial();
            tempTutorial.setTitle(tutorial.getTitle());
            tempTutorial.setDescription(tutorial.getDescription());
            tempTutorial.setPublished(tutorial.isPublished());

            Tutorial _tutorial = tutorialService.saveTutorial(tempTutorial);
            return new ResponseEntity<>(_tutorial,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial){
        Tutorial _tutorial = tutorialService.findById(id);
        if (_tutorial!=null){
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialService.saveTutorial(_tutorial),HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id){
        try{
            tutorialService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorial(){
        try{
            tutorialService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished(){
        try {
            List<Tutorial> tutorialList = tutorialService.findByPublished(true);
            if (!tutorialList.isEmpty())
                return new ResponseEntity<>(tutorialList,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
