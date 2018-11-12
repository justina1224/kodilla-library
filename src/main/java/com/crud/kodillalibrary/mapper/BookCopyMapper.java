package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.BookCopy;
import com.crud.kodillalibrary.domain.BookCopyDto;
import com.crud.kodillalibrary.domain.BookStatus;
import com.crud.kodillalibrary.domain.BookTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookCopyMapper {

    @Autowired
    private BookTitleMapper bookTitleMapper;

    public BookCopy mapToBookCopy(final BookCopyDto bookCopyDto) {
        return new BookCopy (
                bookCopyDto.getId(),
                bookTitleMapper.mapToBookTitle(bookCopyDto.getBookTitleDto()),
                bookCopyDto.getBookStatus());
    }

    public BookCopy mapToBookCopyByTitle(final BookTitle bookTitle) {
        return new BookCopy (
                bookTitle,
                BookStatus.AVAILABLE);

    }

    public BookCopyDto mapToBookCopyDto(final BookCopy bookCopy) {
        return new BookCopyDto(
                bookCopy.getId(),
                bookTitleMapper.mapToBookTitleDto(bookCopy.getBookTitle()),
                bookCopy.getBookStatus());
    }

    public List<BookCopyDto> mapToBookCopyDtoList(final List<BookCopy> copiesList) {
        return copiesList.stream()
                .map(this::mapToBookCopyDto)
                .collect(Collectors.toList());

    }
}
