package com.example.habittracker.controller;

import com.example.habittracker.dto.HabitCreateDTO;
import com.example.habittracker.dto.HabitDTO;
import com.example.habittracker.service.HabitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/habits")
@Tag(name = "Habits", description = "Endpoints para gerenciar hábitos")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @Operation(summary = "Listar todos os hábitos", description = "Retorna uma lista de todos os hábitos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<HabitDTO> getAll() {
        return habitService.findAll();
    }

    @Operation(summary = "Buscar hábito por ID", description = "Retorna os dados de um hábito com base no ID informado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Hábito encontrado"),
        @ApiResponse(responseCode = "404", description = "Hábito não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> getById(@PathVariable Long id) {
        HabitDTO habit = habitService.findById(id);
        return ResponseEntity.ok(habit);
    }

    @Operation(summary = "Criar novo hábito", description = "Cadastra um novo hábito")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Hábito criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<HabitDTO> create(@RequestBody @Valid HabitCreateDTO dto) {
        HabitDTO created = habitService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Atualizar hábito", description = "Atualiza os dados de um hábito existente")
    @PutMapping("/{id}")
    public ResponseEntity<HabitDTO> update(@PathVariable Long id, @RequestBody HabitDTO dto) {
        HabitDTO updated = habitService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Excluir hábito", description = "Exclui um hábito com base no ID informado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
