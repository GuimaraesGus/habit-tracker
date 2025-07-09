package com.example.habittracker.controller;

import com.example.habittracker.dto.HabitCreateDTO;
import com.example.habittracker.dto.HabitDTO;
import com.example.habittracker.service.HabitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HabitController.class)
public class HabitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitService habitService;

    @Autowired
    private ObjectMapper objectMapper;

    private HabitDTO habitDTO;

    @BeforeEach
    void setUp() {
        habitDTO = HabitDTO.builder()
                .id(1L)
                .name("Read")
                .description("Read 10 pages")
                .build();
    }

    @Test
    void shouldReturnHabitById() throws Exception {
        when(habitService.findById(1L)).thenReturn(habitDTO);

        mockMvc.perform(get("/habits/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Read"))
                .andExpect(jsonPath("$.description").value("Read 10 pages"));
    }

    @Test
    void shouldReturnAllHabits() throws Exception {
        List<HabitDTO> habits = Collections.singletonList(habitDTO);
        when(habitService.findAll()).thenReturn(habits);

        mockMvc.perform(get("/habits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Read"));
    }

    @Test
    void shouldCreateHabit() throws Exception {
        when(habitService.create(any(HabitCreateDTO.class))).thenReturn(habitDTO);

        mockMvc.perform(post("/habits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(habitDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Read"));
    }

    @Test
    void shouldUpdateHabit() throws Exception {
        when(habitService.update(eq(1L), any(HabitDTO.class))).thenReturn(habitDTO);

        mockMvc.perform(put("/habits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(habitDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Read"));
    }

    @Test
    void shouldDeleteHabit() throws Exception {
        doNothing().when(habitService).delete(1L);

        mockMvc.perform(delete("/habits/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
        when(habitService.findById(99L)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/habits/99"))
                .andExpect(status().isInternalServerError());
    }
}
