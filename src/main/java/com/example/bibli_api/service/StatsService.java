package com.example.bibli_api.service;

import com.example.bibli_api.domain.Category;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatsService {

    private final BookService bookService;
    private final AuthorService authorService;

    public StatsService(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // Nombre total de livres
    public long totalBooks() {
        return bookService.countBooks();
    }

    // Nombre total d'auteurs
    public long totalAuthors() {
        return authorService.countAuthors();
    }

    // Répartition des livres par catégorie
    public Map<String, Long> booksByCategory() {
        Map<String, Long> result = new HashMap<>();

        for (Category category : Category.values()) {
            long count = bookService.countByCategory(category);
            result.put(category.name(), count);
        }

        return result;
    }
}
