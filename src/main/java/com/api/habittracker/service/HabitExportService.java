package com.api.habittracker.service;

import com.api.habittracker.entity.Habit;
import com.api.habittracker.repository.HabitRepository;
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
            //Resgata as informações atualizadas do banco
            List<Habit> habits = repository.findAll();

            //Configura o local no qual o arquivo será exportado
            String timestamp = LocalDateTime.now().toString().replace(":", "-");
            String fileName = "export/habits-export-" + timestamp + ".json";
            File file = Paths.get(fileName).toFile();

            //Grava o arquivo
            objectMapper.writeValue(file, habits);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
