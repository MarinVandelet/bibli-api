package com.example.bibli_api.service;

import com.example.bibli_api.domain.Author;
import com.example.bibli_api.domain.Book;
import com.example.bibli_api.domain.Category;
import com.example.bibli_api.dto.AuthorResponseDto;
import com.example.bibli_api.dto.BookRequestDto;
import com.example.bibli_api.dto.BookResponseDto;
import com.example.bibli_api.exception.ResourceNotFoundException;
import com.example.bibli_api.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    // Création d'un livre
    public BookResponseDto create(BookRequestDto dto) {
        Author author = authorService.getById(dto.getAuthorId());
        Category category = parseCategory(dto.getCategory());

        Book book = new Book(
                dto.getTitle(),
                dto.getIsbn(),
                dto.getYear(),
                category,
                author
        );
        bookRepository.save(book);

        return toDto(book);
    }

    // Récupération entité
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    // Récupération DTO
    public BookResponseDto getDtoById(Long id) {
        return toDto(getById(id));
    }

    // Mise à jour
    public BookResponseDto update(Long id, BookRequestDto dto) {
        Book book = getById(id);

        Author author = authorService.getById(dto.getAuthorId());
        Category category = parseCategory(dto.getCategory());

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setYear(dto.getYear());
        book.setCategory(category);
        book.setAuthor(author);

        bookRepository.save(book);
        return toDto(book);
    }

    // Suppression
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    // Recherche avec filtres + pagination
    public Page<BookResponseDto> search(
            String title,
            String category,
            Long authorId,
            Integer fromYear,
            Integer toYear,
            Pageable pageable
    ) {
        List<Book> all = bookRepository.findAll();
        List<Book> filtered = new ArrayList<>(all);

        if (title != null && !title.isBlank()) {
            String t = title.toLowerCase(Locale.ROOT);
            filtered = filtered.stream()
                    .filter(b -> b.getTitle() != null &&
                            b.getTitle().toLowerCase(Locale.ROOT).contains(t))
                    .collect(Collectors.toList());
        }

        if (category != null && !category.isBlank()) {
            Category c = parseCategory(category);
            filtered = filtered.stream()
                    .filter(b -> b.getCategory() == c)
                    .collect(Collectors.toList());
        }

        if (authorId != null) {
            filtered = filtered.stream()
                    .filter(b -> b.getAuthor() != null &&
                            authorId.equals(b.getAuthor().getId()))
                    .collect(Collectors.toList());
        }

        if (fromYear != null) {
            filtered = filtered.stream()
                    .filter(b -> b.getYear() != null && b.getYear() >= fromYear)
                    .collect(Collectors.toList());
        }

        if (toYear != null) {
            filtered = filtered.stream()
                    .filter(b -> b.getYear() != null && b.getYear() <= toYear)
                    .collect(Collectors.toList());
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<BookResponseDto> content;

        if (start >= filtered.size()) {
            content = new ArrayList<>();
        } else {
            content = filtered.subList(start, end)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }

        return new PageImpl<>(content, pageable, filtered.size());
    }

    // Stats
    public long countBooks() {
        return bookRepository.count();
    }

    public long countByCategory(Category category) {
        return bookRepository.countByCategory(category);
    }

    // --------- Méthodes utilitaires ---------

    private Category parseCategory(String value) {
        try {
            return Category.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Unknown category: " + value);
        }
    }

    private BookResponseDto toDto(Book book) {
        Author a = book.getAuthor();
        AuthorResponseDto authorDto = new AuthorResponseDto(
                a.getId(),
                a.getFirstName(),
                a.getLastName(),
                a.getBirthYear()
        );

        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getYear(),
                book.getCategory().name(),
                authorDto
        );
    }
}
