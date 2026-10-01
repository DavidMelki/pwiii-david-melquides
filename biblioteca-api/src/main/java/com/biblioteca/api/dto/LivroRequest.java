package com.biblioteca.api.dto;

import com.biblioteca.api.model.Genero;
import jakarta.validation.constraints.*;

public record LivroRequest(

        @NotBlank(message = "O título é obrigatório")
        @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
        String titulo,

        @NotBlank(message = "O autor é obrigatório")
        @Size(max = 120, message = "O autor deve ter no máximo 120 caracteres")
        String autor,

        @NotBlank(message = "O ISBN é obrigatório")
        @Pattern(regexp = "^(?:\\d[- ]?){9}[\\dXx]$|^(?:\\d[- ]?){12}\\d$",
                message = "ISBN inválido (use 10 ou 13 dígitos, com ou sem hífens)")
        String isbn,

        @NotNull(message = "O ano de publicação é obrigatório")
        @Min(value = 1450, message = "O ano de publicação deve ser 1450 ou posterior")
        @Max(value = 2100, message = "O ano de publicação deve ser 2100 ou anterior")
        Integer anoPublicacao,

        @NotNull(message = "O gênero é obrigatório")
        Genero genero,

        Boolean disponivel
) {
}
