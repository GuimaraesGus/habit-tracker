package com.example.habittracker.service;

import com.example.habittracker.dto.HabitCreateDTO;
import com.example.habittracker.dto.HabitDTO;
import com.example.habittracker.entity.Habit;
import com.example.habittracker.mapper.HabitMapper;
import com.example.habittracker.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HabitServiceTest {

    @Mock
    private HabitRepository repository;

    @Mock
    private HabitMapper mapper;

    @Mock
    private HabitExportService exportService;

    @InjectMocks
    private HabitService service;

    private Habit habit;
    private HabitDTO dto;
    private HabitCreateDTO createDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        habit = Habit.builder().id(1L).name("Read").description("Read daily").build();
        dto = HabitDTO.builder().id(1L).name("Read").description("Read daily").build();
    }

    @Test
    void shouldCreateHabitSuccessfully() {
        when(mapper.toEntity(createDTO)).thenReturn(habit);
        when(repository.save(habit)).thenReturn(habit);
        when(mapper.toDTO(habit)).thenReturn(dto);

        HabitDTO created = service.create(createDTO);

        assertNotNull(created);
        assertEquals(dto.getId(), created.getId());
        verify(exportService, times(1)).exportModifiedHabits();
    }

    @Test
    void shouldReturnAllHabits() {
        when(repository.findAll()).thenReturn(Arrays.asList(habit));
        when(mapper.toDTO(habit)).thenReturn(dto);

        List<HabitDTO> habits = service.findAll();

        assertNotNull(habits);
        assertEquals(1, habits.size());
        assertEquals("Read", habits.get(0).getName());
    }

    @Test
    void shouldReturnHabitById() {
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        when(mapper.toDTO(habit)).thenReturn(dto);

        HabitDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateHabitSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        when(mapper.toEntity(dto)).thenReturn(habit);
        when(repository.save(habit)).thenReturn(habit);
        when(mapper.toDTO(habit)).thenReturn(dto);

        HabitDTO updated = service.update(1L, dto);

        assertNotNull(updated);
        assertEquals("Read", updated.getName());
    }

    @Test
    void shouldDeleteHabitById() {
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(1)).deleteById(1L);
    }
}
