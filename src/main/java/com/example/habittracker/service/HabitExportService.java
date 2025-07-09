package com.example.habittracker.service;

import com.example.habittracker.entity.Habit;
import com.example.habittracker.repository.HabitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitExportService {
    private final HabitRepository repository;
    private final ObjectMapper objectMapper;

    public void exportModifiedHabits() {
        try {
            List<Habit> habits = repository.findAll();
            String timestamp = LocalDateTime.now().toString().replace(":", "-");
            String fileName = "export/habits-export-" + timestamp + ".json";
            File file = Paths.get(fileName).toFile();
            objectMapper.writeValue(file, habits);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
