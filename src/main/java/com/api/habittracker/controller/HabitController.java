package com.api.habittracker.controller;

import com.api.habittracker.dto.HabitCreateDTO;
import com.api.habittracker.dto.HabitDTO;
import com.api.habittracker.dto.HabitUpdateDTO;
import com.api.habittracker.service.HabitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
    @ApiResponse(responseCode = "200", description = "Lista de hábitos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<HabitDTO>> getAll() {
        List<HabitDTO> habits = habitService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(habits);
    }

    @Operation(summary = "Buscar hábito por ID", description = "Retorna os dados de um hábito com base no ID informado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Hábito encontrado"),
        @ApiResponse(responseCode = "404", description = "Hábito não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> getById(@PathVariable Long id) {
        HabitDTO habit = habitService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(habit);
    }

    @Operation(summary = "Criar novo hábito", description = "Cadastra um novo hábito")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Hábito criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<HabitDTO> create(@RequestBody @Valid HabitCreateDTO dto) {
        HabitDTO habit = habitService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(habit);
    }

    @Operation(summary = "Atualizar hábito", description = "Atualiza os dados de um hábito existente")
    @PutMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Hábito atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Hábito não encontrado", content = @Content)
    })
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody HabitUpdateDTO dto) {
        habitService.update(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Excluir hábito", description = "Exclui um hábito com base no ID informado")
    @DeleteMapping("/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Hábito deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Hábito não encontrado", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
