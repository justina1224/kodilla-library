package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.BookTitle;
import com.crud.kodillalibrary.domain.BookTitleDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookTitleMapper {

    public BookTitle mapToBookTitle(final BookTitleDto bookTitleDto) {
        return new BookTitle(
                bookTitleDto.getId(),
                bookTitleDto.getTitle(),
                bookTitleDto.getAuthor(),
                bookTitleDto.getIssueDate());
    }

    public BookTitleDto mapToBookTitleDto(final BookTitle bookTitle) {
        return new BookTitleDto(
                bookTitle.getId(),
                bookTitle.getTitle(),
                bookTitle.getAuthor(),
                bookTitle.getIssueDate());
    }

    public List<BookTitleDto> mapToBookTitleDtoList(final List<BookTitle> booksList) {
        return booksList.stream()
                .map(this::mapToBookTitleDto)
                .collect(Collectors.toList());
    }
}
