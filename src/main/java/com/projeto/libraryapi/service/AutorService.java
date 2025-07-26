package com.projeto.libraryapi.service;

import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class AutorService {
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    private final AutorRepository autorRepository;

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }
}
