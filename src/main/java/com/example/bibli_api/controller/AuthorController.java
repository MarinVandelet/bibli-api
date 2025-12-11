package com.example.bibli_api.controller;

import com.example.bibli_api.dto.AuthorRequestDto;
import com.example.bibli_api.dto.AuthorResponseDto;
import com.example.bibli_api.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // POST /authors – créer un auteur
    @PostMapping
    public ResponseEntity<AuthorResponseDto> create(@Valid @RequestBody AuthorRequestDto dto) {
        AuthorResponseDto created = authorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /authors – liste de tous les auteurs
    @GetMapping
    public List<AuthorResponseDto> findAll() {
        return authorService.findAll();
    }

    // GET /authors/{id} – récupérer un auteur
    @GetMapping("/{id}")
    public AuthorResponseDto findById(@PathVariable Long id) {
        return authorService.getDtoById(id);
    }

    // PUT /authors/{id} – mettre à jour un auteur
    @PutMapping("/{id}")
    public AuthorResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequestDto dto
    ) {
        return authorService.update(id, dto);
    }

    // DELETE /authors/{id} – supprimer un auteur
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
