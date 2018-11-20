package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.controller.exception.*;
import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.mapper.BorrowingMapper;
import com.crud.kodillalibrary.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/borrowing")
public class BorrowingController {
    @Autowired
    private DbService dbService;
    @Autowired
    private BorrowingMapper borrowingMapper;

    @GetMapping
    public List<BorrowingDto> getBorrowings() {
        return borrowingMapper.mapToBorrowingDtoList(dbService.getAllBorrowings());
    }

    @GetMapping(value = "{id}")
    public BorrowingDto getBorrowingById(@PathVariable Long id) throws BorrowingNotFoundException {
        return borrowingMapper.mapToBorrowingDto(dbService.getBorrowingById(id)
                .orElseThrow(() -> new BorrowingNotFoundException("Borrowing with id " + id + " doesn't exist")));
    }

    @Transactional
    @DeleteMapping(value = "deleteBorrowing/{id}")
    public void deleteBorrowing(@PathVariable Long id) {
        dbService.deleteBorrowing(id);
    }

    @PutMapping(value = "/updateBorrowingData")
    public BorrowingDto updateBorrowingData(@RequestBody BorrowingDto borrowingDto) {
        return borrowingMapper.mapToBorrowingDto(dbService.saveBorrowing(borrowingMapper.mapToBorrowing(borrowingDto)));
    }

    @PostMapping(value = "/borrow")
    public void borrow(@RequestParam Long readerId, Long titleId) throws ReaderNotFoundException, BookTitleNotFoundException, AvailableCopyNotFoundException {
        LibraryReader reader = dbService.getReaderById(readerId)
                .orElseThrow(() -> new ReaderNotFoundException("Reader with id " + readerId + " doesn't exist"));
        BookTitle title = dbService.getBookTitleById(titleId)
                .orElseThrow(() -> new BookTitleNotFoundException("Title with id " + titleId + " doesn't exist"));
        List<BookCopy> copies = title.getCopies().stream().filter(t -> t.getBookStatus().equals(BookStatus.AVAILABLE)).collect(Collectors.toList());
        if(copies.size() >= 1) {
            BookCopy borrowedCopy = copies.get(0);
            borrowedCopy.setBookStatus(BookStatus.INUSE);
            dbService.saveBorrowing(new Borrowing(reader, borrowedCopy, LocalDate.now(), LocalDate.now().plusDays(30)));
        } else {
            throw new AvailableCopyNotFoundException("There's no available copy of this title");
        }
    }

    @PutMapping(value = "borrowBack")
    public void borrowBack(@RequestParam Long copyId, Long borrowingId) throws BookCopyNotFoundException, BorrowingNotFoundException {
        BookCopy copy = dbService.getBookCopyById(copyId)
                .orElseThrow(() -> new BookCopyNotFoundException("Copy with id " + copyId + " doesn't exist"));
        copy.setBookStatus(BookStatus.AVAILABLE);
        Borrowing borrowing = dbService.getBorrowingById(borrowingId)
                .orElseThrow(() -> new BorrowingNotFoundException("Borrowing with id " + borrowingId + " doesn't exist"));
        borrowing.setReturnDate(LocalDate.now());
        borrowing.setClosed(true);
        dbService.saveBorrowing(borrowing);
    }
}
