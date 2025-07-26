package com.projeto.libraryapi.controller;

import com.projeto.libraryapi.model.Autor;
import com.projeto.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/autores")
public class AutorController {
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    private final AutorService autorService;

    @GetMapping
    public ResponseEntity<Void> ola(){
        return new ResponseEntity("tudo certo", HttpStatus.OK);
    }

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
}
