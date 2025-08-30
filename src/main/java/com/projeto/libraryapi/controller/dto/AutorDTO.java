package com.projeto.libraryapi.controller.dto;

import com.projeto.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotNull(message = "Campo obrigatório")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatório")
        String nacionalidade) {

    public Autor mapearParaAutor(AutorDTO autorDTO) {
        Autor autor = new Autor();
        autor.setNome(autorDTO.nome());
        autor.setDataNascimento(autorDTO.dataNascimento());
        autor.setNacionalidade(autorDTO.nacionalidade());
        return autor;
    }
}
