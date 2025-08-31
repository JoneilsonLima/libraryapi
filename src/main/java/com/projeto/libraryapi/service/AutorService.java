package com.projeto.libraryapi.service;

import com.projeto.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.repository.AutorRepository;
import com.projeto.libraryapi.repository.LivroRepository;
import com.projeto.libraryapi.validator.AutorValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorValidator autorValidator;

    public AutorService(
            AutorRepository autorRepository,
            AutorValidator autorValidator,
            LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
        this.livroRepository = livroRepository;
    }

    public Autor salvar(Autor autor) {
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if(Objects.isNull(autor.getId())) {
            throw new IllegalArgumentException(
                    "Para atualizar, é necessário que o autor já esteja salvo na base.");
        }
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um Autor que possui livros cadastrados!");
        }
        autorRepository.delete(autor);
    }

    private boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade) {
        return autorRepository.buscarPorNomeOuNacionalidade(nome, nacionalidade);
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimentod", "dataCadastro")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }
}
