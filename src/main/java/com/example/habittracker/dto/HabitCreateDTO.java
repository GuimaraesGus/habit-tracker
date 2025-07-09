package com.example.habittracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Objeto usado para criar um novo hábito")
public class HabitCreateDTO {

    @Schema(description = "Nome do hábito", example = "Ler 10 páginas", required = true)
    private String name;

    @Schema(description = "Descrição do hábito", example = "Ler um livro de não-ficção por 30 minutos", required = true)
    private String description;
}