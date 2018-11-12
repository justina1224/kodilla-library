package com.crud.kodillalibrary.service;

import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.repository.BookCopyRepository;
import com.crud.kodillalibrary.repository.BookTitleRepository;
import com.crud.kodillalibrary.repository.BorrowingRepository;
import com.crud.kodillalibrary.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbService {
    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookTitleRepository bookTitleRepository;

    @Autowired
    private BorrowingRepository borrowingRepository;


    public List<LibraryReader> getAllReaders() {
        return readerRepository.findAll();
    }

    public LibraryReader saveReader(final LibraryReader libraryReader) {
        return readerRepository.save(libraryReader);
    }

    public Optional<LibraryReader> getReaderById(final Long id) {
        return readerRepository.findById(id);
    }

    public void deleteReader(final Long id) {
        readerRepository.deleteById(id);
    }

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    public BookCopy saveBookCopy(final BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    public Optional<BookCopy> getBookCopyById(final Long id) {
        return bookCopyRepository.findById(id);
    }

    public void deleteBookCopy(final Long id) {
        bookCopyRepository.deleteById(id);
    }

    public List<BookTitle> getAllBookTitles() {
        return bookTitleRepository.findAll();
    }

    public BookTitle saveBookTitle(final BookTitle bookTitle) {
        return bookTitleRepository.save(bookTitle);
    }

    public Optional<BookTitle> getBookTitleById(final Long id) {
        return bookTitleRepository.findById(id);
    }

    public List<BookTitle> getBookTitleByTitle(final String title) {
        return bookTitleRepository.findByTitle(title);
    }

    public void deleteBookTitle(final Long id) {
        bookTitleRepository.deleteById(id);
    }

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public Borrowing saveBorrowing(final Borrowing borrowing) {
        return borrowingRepository.save(borrowing);
    }

    public Optional<Borrowing> getBorrowingById(final Long id) {
        return borrowingRepository.findById(id);
    }

    public void deleteBorrowing(final Long id) {
        borrowingRepository.deleteById(id);
    }
}
