package com.biblioteca.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroResposta(
        LocalDateTime timestamp,
        int status,
        String mensagem,
        Map<String, String> detalhes
) {

    public static ErroResposta de(int status, String mensagem) {
        return new ErroResposta(LocalDateTime.now(), status, mensagem, null);
    }

    public static ErroResposta de(int status, String mensagem, Map<String, String> detalhes) {
        return new ErroResposta(LocalDateTime.now(), status, mensagem, detalhes);
    }
}
