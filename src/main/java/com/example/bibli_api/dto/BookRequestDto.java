package com.example.bibli_api.dto;

import jakarta.validation.constraints.*;

public class BookRequestDto {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "isbn is required")
    @Size(max = 30, message = "isbn must be at most 30 characters")
    private String isbn;

    @NotNull(message = "year is required")
    private Integer year;

    @NotBlank(message = "category is required")
    private String category;

    @NotNull(message = "authorId is required")
    private Long authorId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
