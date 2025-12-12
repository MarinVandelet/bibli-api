package com.example.bibli_api.controller;

import com.example.bibli_api.dto.BookRequestDto;
import com.example.bibli_api.dto.BookResponseDto;
import com.example.bibli_api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // POST /books – créer un livre
    @PostMapping
    public ResponseEntity<BookResponseDto> create(@Valid @RequestBody BookRequestDto dto) {
        BookResponseDto created = bookService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /books/{id} – récupérer un livre
    @GetMapping("/{id}")
    public BookResponseDto findById(@PathVariable Long id) {
        return bookService.getDtoById(id);
    }

    // GET /books – recherche avec filtres
    @GetMapping
    public Page<BookResponseDto> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Integer fromYear,
            @RequestParam(required = false) Integer toYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookService.search(
                title,
                category,
                authorId,
                fromYear,
                toYear,
                PageRequest.of(page, size)
        );
    }

    // PUT /books/{id} – modifier un livre
    @PutMapping("/{id}")
    public BookResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDto dto
    ) {
        return bookService.update(id, dto);
    }

    // DELETE /books/{id} – supprimer un livre
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
