package com.example.bibli_api.service;

import com.example.bibli_api.domain.Author;
import com.example.bibli_api.dto.AuthorRequestDto;
import com.example.bibli_api.dto.AuthorResponseDto;
import com.example.bibli_api.exception.ResourceNotFoundException;
import com.example.bibli_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Création
    public AuthorResponseDto create(AuthorRequestDto dto) {
        Author author = new Author(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthYear()
        );
        authorRepository.save(author);
        return toDto(author);
    }

    // Liste de tous les auteurs
    public List<AuthorResponseDto> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Récupération d'un auteur (entité) – utilisé par BookService
    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
    }

    // Récupération d'un auteur (DTO) – pour l'API REST
    public AuthorResponseDto getDtoById(Long id) {
        Author author = getById(id);
        return toDto(author);
    }

    // Mise à jour
    public AuthorResponseDto update(Long id, AuthorRequestDto dto) {
        Author author = getById(id);

        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthYear(dto.getBirthYear());

        authorRepository.save(author);
        return toDto(author);
    }

    // Suppression
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found");
        }
        authorRepository.deleteById(id);
    }

    // Stats : nombre total d'auteurs
    public long countAuthors() {
        return authorRepository.count();
    }

    // Mapping entité -> DTO
    private AuthorResponseDto toDto(Author author) {
        return new AuthorResponseDto(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthYear()
        );
    }
}
