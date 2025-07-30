package com.projeto.libraryapi.service;

import com.projeto.libraryapi.controller.AutorDTO;
import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }
}
