package com.crud.kodillalibrary.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BorrowingDto {
    private Long id;
    private LibraryReader reader;
    private BookCopy bookCopy;
    private LocalDate borrowingDate;
    private LocalDate returnDate;
    private boolean isClosed = false;

    public BorrowingDto(LibraryReader reader, BookCopy bookCopy, LocalDate borrowingDate, LocalDate returnDate, boolean isClosed) {
        this.reader = reader;
        this.bookCopy = bookCopy;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.isClosed = isClosed;
    }
}
