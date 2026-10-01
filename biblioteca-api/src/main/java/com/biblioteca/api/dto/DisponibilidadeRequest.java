package com.biblioteca.api.dto;

import jakarta.validation.constraints.NotNull;

public record DisponibilidadeRequest(
        @NotNull(message = "O campo 'disponivel' é obrigatório")
        Boolean disponivel
) {
}
