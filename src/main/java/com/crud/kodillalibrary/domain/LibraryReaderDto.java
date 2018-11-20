package com.crud.kodillalibrary.domain;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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
