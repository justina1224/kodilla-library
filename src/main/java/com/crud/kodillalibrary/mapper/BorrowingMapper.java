package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.Borrowing;
import com.crud.kodillalibrary.domain.BorrowingDto;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class BorrowingMapper {
    public Borrowing mapToBorrowing(final BorrowingDto borrowingDto) {
        return new Borrowing(
                borrowingDto.getId(),
                borrowingDto.getReader(),
                borrowingDto.getBookCopy(),
                borrowingDto.getBorrowingDate(),
                borrowingDto.getReturnDate(),
                borrowingDto.isClosed());
    }

    public BorrowingDto mapToBorrowingDto(final Borrowing borrowing) {
        return new BorrowingDto(
                borrowing.getId(),
                borrowing.getReader(),
                borrowing.getBookCopy(),
                borrowing.getBorrowingDate(),
                borrowing.getReturnDate(),
                borrowing.isClosed());
    }

    public List<BorrowingDto> mapToBorrowingDtoList(final List<Borrowing> borrowedBooksList) {
        return borrowedBooksList.stream()
                .map(this::mapToBorrowingDto)
                .collect(Collectors.toList());
    }
}
