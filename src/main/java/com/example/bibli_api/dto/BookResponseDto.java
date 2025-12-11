package com.example.bibli_api.dto;

public class BookResponseDto {

    private Long id;
    private String title;
    private String isbn;
    private Integer year;
    private String category;
    private AuthorResponseDto author;

    public BookResponseDto(Long id, String title, String isbn, Integer year, String category, AuthorResponseDto author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.category = category;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public AuthorResponseDto getAuthor() {
        return author;
    }
}
