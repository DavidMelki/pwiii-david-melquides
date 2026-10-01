package com.biblioteca.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ManipuladorGlobalExcecoes {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> naoEncontrado(RecursoNaoEncontradoException ex) {
        return resposta(HttpStatus.NOT_FOUND, ErroResposta.de(404, ex.getMessage()));
    }

    @ExceptionHandler(IsbnDuplicadoException.class)
    public ResponseEntity<ErroResposta> conflito(IsbnDuplicadoException ex) {
        return resposta(HttpStatus.CONFLICT, ErroResposta.de(409, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> validacao(MethodArgumentNotValidException ex) {
        Map<String, String> detalhes = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(erro -> detalhes.putIfAbsent(erro.getField(), erro.getDefaultMessage()));
        return resposta(HttpStatus.BAD_REQUEST,
                ErroResposta.de(400, "Dados inválidos na requisição", detalhes));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> corpoIlegivel(HttpMessageNotReadableException ex) {
        return resposta(HttpStatus.BAD_REQUEST,
                ErroResposta.de(400, "Corpo da requisição ausente ou malformado (verifique o JSON e o valor do gênero)"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResposta> parametroInvalido(MethodArgumentTypeMismatchException ex) {
        return resposta(HttpStatus.BAD_REQUEST,
                ErroResposta.de(400, "Valor inválido para o parâmetro '" + ex.getName() + "'"));
    }

    private ResponseEntity<ErroResposta> resposta(HttpStatus status, ErroResposta corpo) {
        return ResponseEntity.status(status).body(corpo);
    }
}
