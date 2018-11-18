package com.crud.kodillalibrary.dao;

import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.repository.BookCopyRepository;
import com.crud.kodillalibrary.repository.BookTitleRepository;
import com.crud.kodillalibrary.repository.BorrowingRepository;
import com.crud.kodillalibrary.repository.ReaderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowingRepositoryTestSuite {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BookCopyRepository copyRepository;

    @Autowired
    private BookTitleRepository titleRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Test
    public void testSaveBorrowing() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookCopy bookCopy = new BookCopy(bookTitle, BookStatus.AVAILABLE);
        LibraryReader reader = new LibraryReader("Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(reader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30));

        //When
        titleRepository.save(bookTitle);
        copyRepository.save(bookCopy);
        readerRepository.save(reader);
        borrowingRepository.save(borrowing);
        Long titleId = bookTitle.getId();
        Long copyId = bookCopy.getId();
        Long readerId = reader.getId();
        Long borrowingId = borrowing.getId();

        Borrowing savedBorrowing = borrowingRepository.findOne(borrowingId);

        //Then
        Assert.assertEquals(copyId, savedBorrowing.getBookCopy().getId());
        Assert.assertEquals(readerId, savedBorrowing.getReader().getId());
        Assert.assertEquals(LocalDate.now(), savedBorrowing.getBorrowingDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), savedBorrowing.getReturnDate());

        //Cleanup
        borrowingRepository.deleteById(borrowingId);
        readerRepository.deleteById(readerId);
        copyRepository.deleteById(copyId);
        titleRepository.deleteById(titleId);
    }

    @Test
    public void testfindAllBorrowings() {
        //Given
        BookTitle bookTitle1 = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookCopy bookCopy1 = new BookCopy(bookTitle1, BookStatus.AVAILABLE);
        BookCopy bookCopy2 = new BookCopy(bookTitle1, BookStatus.AVAILABLE);
        LibraryReader reader1 = new LibraryReader("Jan", "Kowalski", LocalDate.now());
        LibraryReader reader2 = new LibraryReader("Marzena", "Zyznawska", LocalDate.now().minusDays(2));
        Borrowing borrowing1 = new Borrowing(reader1, bookCopy1, LocalDate.now(), LocalDate.now().plusDays(30));
        Borrowing borrowing2 = new Borrowing(reader2, bookCopy2, LocalDate.now().minusDays(1), LocalDate.now().plusDays(29));

        //When
        titleRepository.save(bookTitle1);
        copyRepository.save(bookCopy1);
        copyRepository.save(bookCopy2);
        readerRepository.save(reader1);
        readerRepository.save(reader2);
        borrowingRepository.save(borrowing1);
        borrowingRepository.save(borrowing2);
        Long titleId1 = bookTitle1.getId();
        Long copyId1 = bookCopy1.getId();
        Long copyId2 = bookCopy2.getId();
        Long readerId1 = reader1.getId();
        Long readerId2 = reader2.getId();
        Long borrowingId1 = borrowing1.getId();
        Long borrowingId2 = borrowing2.getId();

        List<Borrowing> allBorrowings = borrowingRepository.findAll();

        //Then
        Assert.assertEquals(2, allBorrowings.size());
        Assert.assertEquals(copyId1, allBorrowings.get(0).getBookCopy().getId());
        Assert.assertEquals(readerId1, allBorrowings.get(0).getReader().getId());
        Assert.assertEquals(LocalDate.now(), allBorrowings.get(0).getBorrowingDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), allBorrowings.get(0).getReturnDate());
        Assert.assertEquals(copyId2, allBorrowings.get(1).getBookCopy().getId());
        Assert.assertEquals(readerId2, allBorrowings.get(1).getReader().getId());
        Assert.assertEquals(LocalDate.now().minusDays(1), allBorrowings.get(1).getBorrowingDate());
        Assert.assertEquals(LocalDate.now().plusDays(29), allBorrowings.get(1).getReturnDate());

        //Cleanup
        borrowingRepository.deleteById(borrowingId1);
        borrowingRepository.deleteById(borrowingId2);
        readerRepository.deleteById(readerId1);
        readerRepository.deleteById(readerId2);
        copyRepository.deleteById(copyId1);
        copyRepository.deleteById(copyId2);
        titleRepository.deleteById(titleId1);
    }

    @Test
    public void testfindBorrowingById() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookCopy bookCopy = new BookCopy(bookTitle, BookStatus.AVAILABLE);
        LibraryReader reader = new LibraryReader("Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(reader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30));

        //When
        titleRepository.save(bookTitle);
        copyRepository.save(bookCopy);
        readerRepository.save(reader);
        borrowingRepository.save(borrowing);
        Long titleId = bookTitle.getId();
        Long copyId = bookCopy.getId();
        Long readerId = reader.getId();
        Long borrowingId = borrowing.getId();

        Borrowing foundBorrowing = borrowingRepository.findById(borrowingId).orElse(new Borrowing());

        //Then
        Assert.assertEquals(copyId, foundBorrowing.getBookCopy().getId());
        Assert.assertEquals(readerId, foundBorrowing.getReader().getId());
        Assert.assertEquals(LocalDate.now(), foundBorrowing.getBorrowingDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), foundBorrowing.getReturnDate());

        //Cleanup
        borrowingRepository.deleteById(borrowingId);
        readerRepository.deleteById(readerId);
        copyRepository.deleteById(copyId);
        titleRepository.deleteById(titleId);
    }

    @Test
    public void testDeleteBorrowingById() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookCopy bookCopy = new BookCopy(bookTitle, BookStatus.AVAILABLE);
        LibraryReader reader = new LibraryReader("Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(reader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30));

        //When
        titleRepository.save(bookTitle);
        copyRepository.save(bookCopy);
        readerRepository.save(reader);
        borrowingRepository.save(borrowing);
        Long titleId = bookTitle.getId();
        Long copyId = bookCopy.getId();
        Long readerId = reader.getId();
        Long borrowingId = borrowing.getId();

        borrowingRepository.deleteById(borrowingId);
        List<Borrowing> borrowings = borrowingRepository.findAll();

        //Then
        Assert.assertEquals(0, borrowings.size());

        //Cleanup
        readerRepository.deleteById(readerId);
        copyRepository.deleteById(copyId);
        titleRepository.deleteById(titleId);
    }
}
