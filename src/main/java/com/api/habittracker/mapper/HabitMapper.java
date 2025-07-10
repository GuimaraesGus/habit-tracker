package com.api.habittracker.mapper;

import com.api.habittracker.dto.HabitCreateDTO;
import com.api.habittracker.dto.HabitDTO;
import com.api.habittracker.entity.Habit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HabitMapper {
    Habit toEntity(HabitCreateDTO dto);
    Habit toEntity(HabitDTO dto);
    HabitDTO toDTO(Habit entity);
}
