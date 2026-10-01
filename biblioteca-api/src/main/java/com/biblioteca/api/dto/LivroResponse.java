package com.biblioteca.api.dto;

import com.biblioteca.api.model.Genero;
import com.biblioteca.api.model.Livro;

import java.time.LocalDateTime;

public record LivroResponse(
        Long id,
        String titulo,
        String autor,
        String isbn,
        Integer anoPublicacao,
        Genero genero,
        boolean disponivel,
        LocalDateTime dataCadastro
) {

    public static LivroResponse de(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getGenero(),
                livro.isDisponivel(),
                livro.getDataCadastro()
        );
    }
}
