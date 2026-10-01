package com.biblioteca.api.service;

import com.biblioteca.api.dto.LivroRequest;
import com.biblioteca.api.dto.LivroResponse;
import com.biblioteca.api.exception.IsbnDuplicadoException;
import com.biblioteca.api.exception.RecursoNaoEncontradoException;
import com.biblioteca.api.model.Genero;
import com.biblioteca.api.model.Livro;
import com.biblioteca.api.repository.LivroRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LivroService {

    private static final Sort ORDEM_PADRAO = Sort.by(Sort.Direction.ASC, "titulo");

    private final LivroRepository repositorio;

    public LivroService(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<LivroResponse> listar(String termo, Genero genero) {
        boolean temTermo = termo != null && !termo.isBlank();
        String busca = temTermo ? termo.trim() : null;

        List<Livro> livros;
        if (temTermo && genero != null) {
            livros = repositorio.buscarPorTermoEGenero(busca, genero, ORDEM_PADRAO);
        } else if (temTermo) {
            livros = repositorio.buscarPorTermo(busca, ORDEM_PADRAO);
        } else if (genero != null) {
            livros = repositorio.findByGenero(genero, ORDEM_PADRAO);
        } else {
            livros = repositorio.findAll(ORDEM_PADRAO);
        }

        return livros.stream().map(LivroResponse::de).toList();
    }

    public LivroResponse buscarPorId(Long id) {
        return LivroResponse.de(obterLivro(id));
    }

    @Transactional
    public LivroResponse cadastrar(LivroRequest dados) {
        String isbn = normalizarIsbn(dados.isbn());
        if (repositorio.existsByIsbn(isbn)) {
            throw new IsbnDuplicadoException(isbn);
        }

        Livro livro = new Livro(
                dados.titulo().trim(),
                dados.autor().trim(),
                isbn,
                dados.anoPublicacao(),
                dados.genero(),
                dados.disponivel() == null || dados.disponivel()
        );
        return LivroResponse.de(repositorio.save(livro));
    }

    @Transactional
    public LivroResponse atualizar(Long id, LivroRequest dados) {
        Livro livro = obterLivro(id);

        String isbn = normalizarIsbn(dados.isbn());
        if (repositorio.existsByIsbnAndIdNot(isbn, id)) {
            throw new IsbnDuplicadoException(isbn);
        }

        livro.setTitulo(dados.titulo().trim());
        livro.setAutor(dados.autor().trim());
        livro.setIsbn(isbn);
        livro.setAnoPublicacao(dados.anoPublicacao());
        livro.setGenero(dados.genero());
        if (dados.disponivel() != null) {
            livro.setDisponivel(dados.disponivel());
        }
        return LivroResponse.de(repositorio.save(livro));
    }

    @Transactional
    public LivroResponse alterarDisponibilidade(Long id, boolean disponivel) {
        Livro livro = obterLivro(id);
        livro.setDisponivel(disponivel);
        return LivroResponse.de(repositorio.save(livro));
    }

    @Transactional
    public void remover(Long id) {
        repositorio.delete(obterLivro(id));
    }

    private Livro obterLivro(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado (id " + id + ")"));
    }

    private String normalizarIsbn(String isbn) {
        return isbn.replaceAll("[- ]", "").toUpperCase();
    }
}
