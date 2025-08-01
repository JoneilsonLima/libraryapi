package com.projeto.libraryapi.controller;

import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController {
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<Void>  salvar(@RequestBody AutorDTO autor) {
        Autor autorEntidade = autor.mapearParaAutor(autor);
        autorService.salvar(autorEntidade);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
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
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
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
}
