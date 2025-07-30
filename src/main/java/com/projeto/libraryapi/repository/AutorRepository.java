package com.projeto.libraryapi.repository;

import com.projeto.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {
    @Query(value = """
        SELECT * FROM autor
        WHERE nome ILIKE %:nome%
        OR nacionalidade ILIKE %:nacionalidade%
    """, nativeQuery = true)
    List<Autor> buscarPorNomeOuNacionalidade(String nome, String nacionalidade);
}
