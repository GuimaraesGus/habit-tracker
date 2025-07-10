package com.api.habittracker.controller;

import com.api.habittracker.dto.HabitCreateDTO;
import com.api.habittracker.dto.HabitDTO;
import com.api.habittracker.dto.HabitUpdateDTO;
import com.api.habittracker.service.HabitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
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
    private HabitDTO anotherHabitDTO;
    private HabitUpdateDTO habitUpdateDTO;

    @BeforeEach
    void setUp() {
        habitDTO = HabitDTO.builder()
                .id(1L)
                .name("Ler")
                .description("Ler diariamente")
                .build();
        anotherHabitDTO = HabitDTO.builder()
                .id(2L)
                .name("Caminhar")
                .description("Caminhar pela manhã")
                .build();
        habitUpdateDTO = HabitUpdateDTO.builder()
                .name("Comer uma fruta")
                .description("Comer alguma fruta entre 2 refeições")
                .build();
    }

    @Test
    void shouldReturnHabitById() throws Exception {
        when(habitService.findById(1L)).thenReturn(habitDTO);

        mockMvc.perform(get("/habits/1"))
                .andExpect(jsonPath("$.name").value("Ler"));
    }

    @Test
    void shouldReturnAllHabits() throws Exception {
        when(habitService.findAll()).thenReturn(Arrays.asList(habitDTO, anotherHabitDTO));

        mockMvc.perform(get("/habits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ler"))
                .andExpect(jsonPath("$[1].name").value("Caminhar"));
    }

    @Test
    void shouldCreateHabit() throws Exception {
        when(habitService.create(any(HabitCreateDTO.class))).thenReturn(habitDTO);

        mockMvc.perform(post("/habits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(habitDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ler"));
    }

    @Test
    void shouldUpdateHabit() throws Exception {
        doNothing().when(habitService).update(1L, habitUpdateDTO);

        mockMvc.perform(put("/habits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(habitUpdateDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteHabit() throws Exception {
        doNothing().when(habitService).delete(1L);

        mockMvc.perform(delete("/habits/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
        when(habitService.findById(99L)).thenThrow(new EntityNotFoundException("Recurso não encontrado."));

        mockMvc.perform(get("/habits/99"))
                .andExpect(status().isNotFound());
    }
}
