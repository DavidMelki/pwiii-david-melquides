package com.biblioteca.api.controller;

import com.biblioteca.api.dto.DisponibilidadeRequest;
import com.biblioteca.api.dto.LivroRequest;
import com.biblioteca.api.dto.LivroResponse;
import com.biblioteca.api.model.Genero;
import com.biblioteca.api.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService servico;

    public LivroController(LivroService servico) {
        this.servico = servico;
    }

    // GET /api/livros?busca=tolkien&genero=FANTASIA  (ambos os filtros são opcionais)
    @GetMapping
    public List<LivroResponse> listar(@RequestParam(required = false) String busca,
                                      @RequestParam(required = false) Genero genero) {
        return servico.listar(busca, genero);
    }

    // GET /api/livros/generos
    @GetMapping("/generos")
    public List<Genero> listarGeneros() {
        return Arrays.asList(Genero.values());
    }

    // GET /api/livros/{id}
    @GetMapping("/{id}")
    public LivroResponse buscarPorId(@PathVariable Long id) {
        return servico.buscarPorId(id);
    }

    // POST /api/livros
    @PostMapping
    public ResponseEntity<LivroResponse> cadastrar(@Valid @RequestBody LivroRequest dados) {
        LivroResponse criado = servico.cadastrar(dados);
        URI local = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();
        return ResponseEntity.created(local).body(criado);
    }

    // PUT /api/livros/{id}
    @PutMapping("/{id}")
    public LivroResponse atualizar(@PathVariable Long id, @Valid @RequestBody LivroRequest dados) {
        return servico.atualizar(id, dados);
    }

    // PATCH /api/livros/{id}/disponibilidade
    @PatchMapping("/{id}/disponibilidade")
    public LivroResponse alterarDisponibilidade(@PathVariable Long id,
                                                @Valid @RequestBody DisponibilidadeRequest dados) {
        return servico.alterarDisponibilidade(id, dados.disponivel());
    }

    // DELETE /api/livros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        servico.remover(id);
        return ResponseEntity.noContent().build();
    }
}
