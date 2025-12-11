package com.example.bibli_api.controller;

import com.example.bibli_api.service.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    // GET /stats/books – nombre total de livres
    @GetMapping("/books")
    public long totalBooks() {
        return statsService.totalBooks();
    }

    // GET /stats/authors – nombre total d'auteurs
    @GetMapping("/authors")
    public long totalAuthors() {
        return statsService.totalAuthors();
    }

    // GET /stats/books/by-category – répartition par catégorie
    @GetMapping("/books/by-category")
    public Map<String, Long> booksByCategory() {
        return statsService.booksByCategory();
    }
}
