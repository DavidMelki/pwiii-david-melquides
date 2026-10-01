package com.biblioteca.api.exception;

public class IsbnDuplicadoException extends RuntimeException {

    public IsbnDuplicadoException(String isbn) {
        super("Já existe um livro cadastrado com o ISBN " + isbn);
    }
}
