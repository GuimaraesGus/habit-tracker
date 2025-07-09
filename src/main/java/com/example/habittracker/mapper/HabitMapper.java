package com.example.habittracker.mapper;

import com.example.habittracker.dto.HabitCreateDTO;
import com.example.habittracker.dto.HabitDTO;
import com.example.habittracker.entity.Habit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HabitMapper {
    Habit toEntity(HabitCreateDTO dto);
    Habit toEntity(HabitDTO dto);
    HabitDTO toDTO(Habit entity);
}
