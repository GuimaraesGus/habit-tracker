package com.example.habittracker.service;

import com.example.habittracker.dto.HabitCreateDTO;
import com.example.habittracker.dto.HabitDTO;
import com.example.habittracker.entity.Habit;
import com.example.habittracker.mapper.HabitMapper;
import com.example.habittracker.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository repository;
    private final HabitMapper mapper;
    private final HabitExportService exportService;

    public HabitDTO findById(Long id) {
        //Procura o registro em banco por id
        Habit habit = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hábito não encontrado com o id " + id));
                
        return mapper.toDTO(habit);
    }

    public List<HabitDTO> findAll() {
        //Procura todos os registros em banco
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public HabitDTO create(HabitCreateDTO dto) {
        //Cria registro em banco
        HabitDTO created = mapper.toDTO(repository.save(mapper.toEntity(dto)));

        //Exporta as alterações para arquivo
        exportService.exportModifiedHabits();

        return created;
    }

    public HabitDTO update(Long id, HabitDTO dto) {
        //Procura o registro em banco por id para ser atualizado
        Habit habit = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hábito não encontrado com o id " + id));

        //Prepara o registro para atualização com as informações novas
        habit.setName(dto.getName());
        habit.setDescription(dto.getDescription());

        //Grava em banco
        HabitDTO updated = mapper.toDTO(repository.save(habit));

        //Exporta as alterações para arquivo
        exportService.exportModifiedHabits();

        return updated;
    }

    public void delete(Long id) {
        //Procura o registro em banco por id para ser deletado
        Habit habit = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));

        //Deleta em banco
        repository.deleteById(habit.getId());
    }
}
