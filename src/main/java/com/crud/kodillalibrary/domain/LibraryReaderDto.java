package com.crud.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LibraryReaderDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate creatingAccountDate;

    public LibraryReaderDto(String firstName, String lastName, LocalDate creatingAccountDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.creatingAccountDate = creatingAccountDate;
    }
}
