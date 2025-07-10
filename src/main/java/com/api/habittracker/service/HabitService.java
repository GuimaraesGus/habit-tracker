package com.api.habittracker.service;

import com.api.habittracker.dto.HabitCreateDTO;
import com.api.habittracker.dto.HabitDTO;
import com.api.habittracker.dto.HabitUpdateDTO;
import com.api.habittracker.entity.Habit;
import com.api.habittracker.mapper.HabitMapper;
import com.api.habittracker.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository repository;
    private final HabitMapper mapper;
    private final HabitExportService exportService;

    public HabitDTO findById(Long id) {
        //Procura o registro em banco por id
        Habit habit = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
                
        return mapper.toDTO(habit);
    }

    public List<HabitDTO> findAll() {
        //Procura todos os registros em banco
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public HabitDTO create(HabitCreateDTO createDTO) {
        //Cria registro em banco
        HabitDTO created = mapper.toDTO(repository.save(mapper.toEntity(createDTO)));

        //Exporta as alterações para arquivo
        exportService.exportModifiedHabits();

        return created;
    }

    public void update(Long id, HabitUpdateDTO dto) {
        //Procura o registro em banco por id para ser atualizado
        Habit habit = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        //Prepara o registro para atualização com as informações novas
        habit.setName(dto.getName());
        habit.setDescription(dto.getDescription());

        //Grava em banco
        repository.save(habit);

        //Exporta as alterações para arquivo
        exportService.exportModifiedHabits();
    }

    public void delete(Long id) {
        //Procura o registro em banco por id para ser deletado
        Habit habit = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        //Deleta em banco
        repository.deleteById(habit.getId());

        //Exporta as alterações para arquivo
        exportService.exportModifiedHabits();
    }
}
