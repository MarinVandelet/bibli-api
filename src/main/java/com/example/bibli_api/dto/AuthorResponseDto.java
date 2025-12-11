package com.example.bibli_api.dto;

public class AuthorResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer birthYear;

    public AuthorResponseDto(Long id, String firstName, String lastName, Integer birthYear) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getBirthYear() {
        return birthYear;
    }
}
