package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowingMapperTestSuite {

    @Autowired
    private BorrowingMapper borrowingMapper;

    @Test
    public void testMapToBorrowing() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test title", "Test author", 2017);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        LibraryReader libraryReader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        BorrowingDto borrowingDto = new BorrowingDto(1L, libraryReader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30), false);
        //When
        Borrowing borrowing = borrowingMapper.mapToBorrowing(borrowingDto);
        //Then
        Assert.assertNotNull(borrowing);
        Assert.assertEquals(Long.valueOf("1"), borrowing.getId());
        Assert.assertEquals(Long.valueOf("1"), borrowing.getReader().getId());
        Assert.assertEquals("Jan", borrowing.getReader().getFirstName());
        Assert.assertEquals("Kowalski", borrowing.getReader().getLastName());
        Assert.assertEquals(LocalDate.now(), borrowing.getReader().getCreatingAccountDate());
        Assert.assertEquals(Long.valueOf("1"), borrowing.getBookCopy().getId());
        Assert.assertEquals(Long.valueOf("1"), borrowing.getBookCopy().getBookTitle().getId());
        Assert.assertEquals("Test title", borrowing.getBookCopy().getBookTitle().getTitle());
        Assert.assertEquals("Test author", borrowing.getBookCopy().getBookTitle().getAuthor());
        Assert.assertEquals(2017, borrowing.getBookCopy().getBookTitle().getIssueDate());
        Assert.assertEquals("available", borrowing.getBookCopy().getBookStatus().getStatus());
        Assert.assertEquals(LocalDate.now(), borrowing.getBorrowingDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), borrowing.getReturnDate());
    }

    @Test
    public void testMapToBorrowingDto() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test title", "Test author", 2017);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        LibraryReader libraryReader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(1L, libraryReader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30), false);
        //When
        BorrowingDto borrowingDto = borrowingMapper.mapToBorrowingDto(borrowing);
        //Then
        Assert.assertNotNull(borrowingDto);
        Assert.assertEquals(Long.valueOf("1"), borrowingDto.getId());
        Assert.assertEquals(Long.valueOf("1"), borrowingDto.getReader().getId());
        Assert.assertEquals("Jan", borrowingDto.getReader().getFirstName());
        Assert.assertEquals("Kowalski", borrowingDto.getReader().getLastName());
        Assert.assertEquals(LocalDate.now(), borrowingDto.getReader().getCreatingAccountDate());
        Assert.assertEquals(Long.valueOf("1"), borrowingDto.getBookCopy().getId());
        Assert.assertEquals(Long.valueOf("1"), borrowingDto.getBookCopy().getBookTitle().getId());
        Assert.assertEquals("Test title", borrowingDto.getBookCopy().getBookTitle().getTitle());
        Assert.assertEquals("Test author", borrowingDto.getBookCopy().getBookTitle().getAuthor());
        Assert.assertEquals(2017, borrowingDto.getBookCopy().getBookTitle().getIssueDate());
        Assert.assertEquals("available", borrowingDto.getBookCopy().getBookStatus().getStatus());
        Assert.assertEquals(LocalDate.now(), borrowingDto.getBorrowingDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), borrowingDto.getReturnDate());
    }

    @Test
    public void testMapToBorrowingDtoList() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test title", "Test author", 2017);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        LibraryReader libraryReader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(1L, libraryReader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30), false);
        List<Borrowing> borrowings = new ArrayList<>();
        borrowings.add(borrowing);
        //When
        List<BorrowingDto> borrowingDtoList = borrowingMapper.mapToBorrowingDtoList(borrowings);
        //Then
        Assert.assertEquals(1, borrowingDtoList.size());
        Assert.assertEquals(Long.valueOf("1"), borrowingDtoList.get(0).getId());
        Assert.assertEquals(Long.valueOf("1"), borrowingDtoList.get(0).getReader().getId());
        Assert.assertEquals("Jan", borrowingDtoList.get(0).getReader().getFirstName());
        Assert.assertEquals("Kowalski", borrowingDtoList.get(0).getReader().getLastName());
        Assert.assertEquals(LocalDate.now(), borrowingDtoList.get(0).getReader().getCreatingAccountDate());
        Assert.assertEquals(Long.valueOf("1"), borrowingDtoList.get(0).getBookCopy().getId());
        Assert.assertEquals(Long.valueOf("1"), borrowingDtoList.get(0).getBookCopy().getBookTitle().getId());
        Assert.assertEquals("Test title", borrowingDtoList.get(0).getBookCopy().getBookTitle().getTitle());
        Assert.assertEquals("Test author", borrowingDtoList.get(0).getBookCopy().getBookTitle().getAuthor());
        Assert.assertEquals(2017, borrowingDtoList.get(0).getBookCopy().getBookTitle().getIssueDate());
        Assert.assertEquals("available", borrowingDtoList.get(0).getBookCopy().getBookStatus().getStatus());
        Assert.assertEquals(LocalDate.now(), borrowingDtoList.get(0).getBorrowingDate());
        Assert.assertEquals(LocalDate.now().plusDays(30), borrowingDtoList.get(0).getReturnDate());
    }
}
