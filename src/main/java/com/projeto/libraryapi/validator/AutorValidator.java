package com.projeto.libraryapi.validator;

import com.projeto.libraryapi.exceptions.RegistroDuplicadoException;
import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorOptional = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );

        if(Objects.isNull(autor.getId())){
            return autorOptional.isPresent();
        }

        return !autor.getId().equals(autorOptional.get().getId()) && autorOptional.isPresent();
    }
}
