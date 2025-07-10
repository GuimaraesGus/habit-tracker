package com.api.habittracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Objeto usado para atualizar novo hábito")
public class HabitUpdateDTO {

    @Schema(description = "Nome do hábito", example = "Comer uma fruta", required = true)
    private String name;

    @Schema(description = "Descrição do hábito", example = "Comer alguma fruta entre 2 refeições", required = true)
    private String description;
}