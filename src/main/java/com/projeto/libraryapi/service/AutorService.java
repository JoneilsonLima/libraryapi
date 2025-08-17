package com.projeto.libraryapi.service;

import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.repository.AutorRepository;
import com.projeto.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;

    public AutorService(AutorRepository autorRepository, AutorValidator autorValidator) {
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
    }

    public Autor salvar(Autor autor) {
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if(Objects.isNull(autor.getId())) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base.");
        }
        autorValidator.validar(autor);
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
