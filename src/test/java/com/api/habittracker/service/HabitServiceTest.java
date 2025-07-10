package com.api.habittracker.service;

import com.api.habittracker.dto.HabitCreateDTO;
import com.api.habittracker.dto.HabitDTO;
import com.api.habittracker.dto.HabitUpdateDTO;
import com.api.habittracker.entity.Habit;
import com.api.habittracker.mapper.HabitMapper;
import com.api.habittracker.repository.HabitRepository;
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
    private Habit anotherHabit;
    private Habit updatedHabit;
    private HabitDTO habitDTO;
    private HabitDTO anotherHabitDTO;
    private HabitCreateDTO habitCreateDTO;
    private HabitUpdateDTO habitUpdateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        habit = Habit.builder().id(1L).name("Ler").description("Ler diariamente").build();
        anotherHabit = Habit.builder().id(2L).name("Caminhar").description("Caminhar pela manhã").build();
        updatedHabit = Habit.builder().id(1L).name("Comer uma fruta").description("Comer alguma fruta entre 2 refeições").build();
        habitDTO = HabitDTO.builder().id(1L).name("Ler").description("Ler diariamente").build();
        anotherHabitDTO = HabitDTO.builder().id(2L).name("Caminhar").description("Caminhar pela manhã").build();
        habitCreateDTO = HabitCreateDTO.builder().name("Ler").description("Ler diariamente").build();
        habitUpdateDTO = HabitUpdateDTO.builder().name("Comer uma fruta").description("Comer alguma fruta entre 2 refeições").build();
    }

    @Test
    void shouldCreateHabitSuccessfully() {
        when(mapper.toEntity(habitCreateDTO)).thenReturn(habit);
        when(repository.save(habit)).thenReturn(habit);
        when(mapper.toDTO(habit)).thenReturn(habitDTO);

        HabitDTO habit = service.create(habitCreateDTO);

        assertNotNull(habit);
        assertEquals(habitDTO.getId(), habit.getId());
        verify(exportService, times(1)).exportModifiedHabits();
    }

    @Test
    void shouldReturnAllHabits() {
        when(repository.findAll()).thenReturn(Arrays.asList(habit, anotherHabit));
        when(mapper.toDTO(habit)).thenReturn(habitDTO);
        when(mapper.toDTO(anotherHabit)).thenReturn(anotherHabitDTO);

        List<HabitDTO> habits = service.findAll();

        assertNotNull(habits);
        assertEquals(2, habits.size());
        assertEquals("Ler", habits.get(0).getName());
        assertEquals("Caminhar", habits.get(1).getName());
    }

    @Test
    void shouldReturnHabitById() {
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        when(mapper.toDTO(habit)).thenReturn(habitDTO);

        HabitDTO habit = service.findById(1L);

        assertNotNull(habit);
        assertEquals("Ler", habit.getName());
    }

    @Test
    void shouldUpdateHabitSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        when(repository.save(updatedHabit)).thenReturn(updatedHabit);

        assertDoesNotThrow(() -> service.update(1L, habitUpdateDTO));

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(updatedHabit);
        verify(exportService, times(1)).exportModifiedHabits();
    }

    @Test
    void shouldDeleteHabitById() {
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(1)).deleteById(1L);
        verify(exportService, times(1)).exportModifiedHabits();
    }
}
