package com.projeto.libraryapi.service;

import com.projeto.libraryapi.controller.AutorDTO;
import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    private final AutorRepository autorRepository;

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if(Objects.isNull(autor.getId())) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base.");
        }
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade) {
        return autorRepository.buscarPorNomeOuNacionalidade(nome, nacionalidade);
    }
}
