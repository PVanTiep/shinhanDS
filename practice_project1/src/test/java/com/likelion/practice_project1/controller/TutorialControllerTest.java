package com.likelion.practice_project1.controller;

import com.likelion.practice_project1.entity.Tutorial;
import com.likelion.practice_project1.service.TutorialService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TutorialController.class)
class TutorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorialService tutorialService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getListTutorials() throws Exception {

        List<Tutorial> tutorialList = new ArrayList<>();
        when(tutorialService.findAll()).thenReturn(tutorialList);
        when(tutorialService.findByTitleContain("title1")).thenReturn(tutorialList);

        //Case 1
        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isNoContent());

        //Case 2
        Tutorial tutorial1 = new Tutorial(1L, "title1", "description1", false);
        Tutorial tutorial2 = new Tutorial(2L, "title2", "description2", false);
        tutorialList.add(tutorial1);
        tutorialList.add(tutorial2);

        mockMvc.perform(get("/api/tutorials").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title1")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[0].published", is(false)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("title2")))
                .andExpect(jsonPath("$[1].description", is("description2")))
                .andExpect(jsonPath("$[1].published", is(false)));

        tutorialList.clear();

        //Case 3
        mockMvc.perform(get("/api/tutorials").param("title", "title1"))
                .andExpect(status().isNoContent());

        //Case 4
        Tutorial tutorial3 = new Tutorial(1L, "title12", "description1", true);
        Tutorial tutorial4 = new Tutorial(2L, "title122", "description2", true);
        tutorialList.add(tutorial3);
        tutorialList.add(tutorial4);

        mockMvc.perform(get("/api/tutorials").param("title", "title1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title12")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[0].published", is(true)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("title122")))
                .andExpect(jsonPath("$[1].description", is("description2")))
                .andExpect(jsonPath("$[1].published", is(true)));
    }

    @Test
    void createTutorial() throws Exception {
        Tutorial newTutorial = new Tutorial(1L, "title1", "des1", false);
        when(tutorialService.save(any(Tutorial.class))).thenReturn(newTutorial);

        mockMvc.perform(post("/api/tutorials")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"title1\", \"description\": \"des1\", \"published\": false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.description").value("des1"))
                .andExpect(jsonPath("$.published").value(false));
    }

    @Test
    void getTutorialById() throws Exception {
        Tutorial responseTutorials = new Tutorial(1L, "title1", "des1", true);
        when(tutorialService.findById(1L)).thenReturn(responseTutorials);

        //Case 1:
        mockMvc.perform(get("/api/tutorials/2"))
                .andExpect(status().isNotFound());

        //Case 2:
        mockMvc.perform(get("/api/tutorials/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.description").value("des1"))
                .andExpect(jsonPath("$.published").value(true));
    }

    @Test
    void updateTutorial() throws Exception {
        Tutorial existTutorials = new Tutorial(1L, "title1", "des1", true);
        Tutorial updatedTutorials = new Tutorial(1L, "updated title1", "updated des1", false);
        when(tutorialService.findById(1L)).thenReturn(existTutorials);
        when(tutorialService.save(any(Tutorial.class))).thenReturn(updatedTutorials);

        //Case 1:
        mockMvc.perform(put("/api/tutorials/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"updated title1\", \"description\": \"updated des1\", \"published\": false}"))
                .andExpect(status().isNotFound());

        //Case 2:
        mockMvc.perform(put("/api/tutorials/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"updated title1\", \"description\": \"updated des1\", \"published\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("updated title1"))
                .andExpect(jsonPath("$.description").value("updated des1"))
                .andExpect(jsonPath("$.published").value(false));
    }

    @Test
    void deleteTutorial() throws Exception {
        doNothing().when(tutorialService).deleteById(1L);

        mockMvc.perform(delete("/api/tutorials/1"))
                .andExpect(status().isOk());

        verify(tutorialService, times(1)).deleteById(1L);
    }

    @Test
    void deleteAllTutorials() throws Exception {
        doNothing().when(tutorialService).deleteAll();

        mockMvc.perform(delete("/api/tutorials"))
                .andExpect(status().isOk());

        verify(tutorialService, times(1)).deleteAll();
    }

    @Test
    void getTutorialsPublished() throws Exception {
        List<Tutorial> tutorialList = new ArrayList<>();
        when(tutorialService.findByPublished()).thenReturn(tutorialList);

        //Case 1:
        mockMvc.perform(get("/api/tutorials/published"))
                .andExpect(status().isNoContent());

        //Case 2:
        Tutorial tutorial1 = new Tutorial(1L, "title1", "description1", true);
        Tutorial tutorial2 = new Tutorial(2L, "title2", "description2", true);
        tutorialList.add(tutorial1);
        tutorialList.add(tutorial2);

        mockMvc.perform(get("/api/tutorials/published"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].published", is(true)))
                .andExpect(jsonPath("$[1].published", is(true)));
    }
}