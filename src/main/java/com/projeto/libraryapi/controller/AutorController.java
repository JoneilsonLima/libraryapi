package com.projeto.libraryapi.controller;

import com.projeto.libraryapi.controller.dto.AutorDTO;
import com.projeto.libraryapi.controller.dto.ErroResposta;
import com.projeto.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.projeto.libraryapi.exceptions.RegistroDuplicadoException;
import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AutorController {
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<Object>  salvar(@RequestBody AutorDTO autor) {
        try {
            Autor autorEntidade = autor.mapearParaAutor(autor);
            autorService.salvar(autorEntidade);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erro = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO autorDTO = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade()
            );

            return ResponseEntity.ok(autorDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            autorService.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erro = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {
        List<AutorDTO> autorDTOList = autorService.pesquisar(nome, nacionalidade)
                .stream()
                .map(autor -> new AutorDTO(
                            autor.getId(),
                            autor.getNome(),
                            autor.getDataNascimento(),
                            autor.getNacionalidade()
                    )
                ).toList();

        return ResponseEntity.ok(autorDTOList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable String id, @RequestBody AutorDTO autorDTO) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();
            autor.setNome(autorDTO.nome());
            autor.setNacionalidade(autorDTO.nacionalidade());
            autor.setDataNascimento(autorDTO.dataNascimento());

            autorService.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            var erro = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }
}
