package com.example.bibli_api.repository;

import com.example.bibli_api.domain.Book;
import com.example.bibli_api.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByCategory(Category category, Pageable pageable);

    Page<Book> findByAuthor_Id(Long authorId, Pageable pageable);

    Page<Book> findByYearBetween(Integer from, Integer to, Pageable pageable);

    long countByCategory(Category category);
}
