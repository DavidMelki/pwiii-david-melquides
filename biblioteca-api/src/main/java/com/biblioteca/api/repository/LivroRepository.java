package com.biblioteca.api.repository;

import com.biblioteca.api.model.Genero;
import com.biblioteca.api.model.Livro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);

    List<Livro> findByGenero(Genero genero, Sort sort);

    @Query("""
            SELECT l FROM Livro l
            WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termo, '%'))
               OR LOWER(l.autor)  LIKE LOWER(CONCAT('%', :termo, '%'))
            """)
    List<Livro> buscarPorTermo(@Param("termo") String termo, Sort sort);

    @Query("""
            SELECT l FROM Livro l
            WHERE l.genero = :genero
              AND (LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR LOWER(l.autor)  LIKE LOWER(CONCAT('%', :termo, '%')))
            """)
    List<Livro> buscarPorTermoEGenero(@Param("termo") String termo,
                                      @Param("genero") Genero genero,
                                      Sort sort);
}
