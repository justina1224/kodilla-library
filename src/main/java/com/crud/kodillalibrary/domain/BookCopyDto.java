package com.crud.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookCopyDto {
    private Long id;
    private BookTitleDto bookTitleDto;
    private BookStatus bookStatus;
    private List<Borrowing> borrowingList;

    public BookCopyDto(BookTitleDto bookTitleDto, BookStatus bookStatus) {
        this.bookTitleDto = bookTitleDto;
        this.bookStatus = bookStatus;
    }

    public BookCopyDto(Long id, BookTitleDto bookTitleDto, BookStatus bookStatus) {
        this.id = id;
        this.bookTitleDto = bookTitleDto;
        this.bookStatus = bookStatus;
    }
}
