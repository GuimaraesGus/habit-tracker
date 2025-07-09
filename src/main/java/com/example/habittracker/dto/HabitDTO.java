package com.example.habittracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Objeto que representa um hábito")
public class HabitDTO {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @Schema(description = "Nome do hábito", example = "Ler 10 páginas")
    private String name;

    @Schema(description = "Descrição do hábito", example = "Ler um livro de não-ficção por 30 minutos")
    private String description;
}
