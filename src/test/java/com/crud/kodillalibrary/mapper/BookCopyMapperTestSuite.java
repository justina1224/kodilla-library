package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookCopyMapperTestSuite {

    @Autowired
    private BookCopyMapper copyMapper;

    @Test
    public void testMapToBookCopy() {
        //Given
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Test title", "Test author", 2017);
        BookCopyDto bookCopyDto = new BookCopyDto(1L, bookTitleDto, BookStatus.AVAILABLE);
        //When
        BookCopy bookCopy = copyMapper.mapToBookCopy(bookCopyDto);
        //Then
        Assert.assertNotNull(bookCopy);
        Assert.assertEquals(Long.valueOf("1"), bookCopy.getId());
        Assert.assertEquals(Long.valueOf("1"), bookCopy.getBookTitle().getId());
        Assert.assertEquals("Test title", bookCopy.getBookTitle().getTitle());
        Assert.assertEquals("Test author", bookCopy.getBookTitle().getAuthor());
        Assert.assertEquals(2017, bookCopy.getBookTitle().getIssueDate());
        Assert.assertEquals("available", bookCopy.getBookStatus().getStatus());
    }

    @Test
    public void testMapToBookCopyByTitle() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test title", "Test author", 2017);
        //When
        BookCopy bookCopy = copyMapper.mapToBookCopyByTitle(bookTitle);
        //Then
        Assert.assertNotNull(bookCopy);
        Assert.assertEquals(Long.valueOf("1"), bookCopy.getBookTitle().getId());
        Assert.assertEquals("Test title", bookCopy.getBookTitle().getTitle());
        Assert.assertEquals("Test author", bookCopy.getBookTitle().getAuthor());
        Assert.assertEquals(2017, bookCopy.getBookTitle().getIssueDate());
        Assert.assertEquals("available", bookCopy.getBookStatus().getStatus());
    }

    @Test
    public void testMapToBookCopyDto() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test title", "Test author", 2017);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        //When
        BookCopyDto bookCopyDto = copyMapper.mapToBookCopyDto(bookCopy);
        //Then
        Assert.assertNotNull(bookCopyDto);
        Assert.assertEquals(Long.valueOf("1"), bookCopyDto.getId());
        Assert.assertEquals(Long.valueOf("1"), bookCopyDto.getBookTitleDto().getId());
        Assert.assertEquals("Test title", bookCopyDto.getBookTitleDto().getTitle());
        Assert.assertEquals("Test author", bookCopyDto.getBookTitleDto().getAuthor());
        Assert.assertEquals(2017, bookCopyDto.getBookTitleDto().getIssueDate());
        Assert.assertEquals("available", bookCopyDto.getBookStatus().getStatus());
    }

    @Test
    public void testMapToBookCopyDtoList() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test title", "Test author", 2017);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        List<BookCopy> copies = new ArrayList<>();
        copies.add(bookCopy);
        //When
        List<BookCopyDto> bookCopies = copyMapper.mapToBookCopyDtoList(copies);
        //Then
        Assert.assertEquals(1, bookCopies.size());
        Assert.assertEquals(Long.valueOf("1"), bookCopies.get(0).getId());
        Assert.assertEquals(Long.valueOf("1"), bookCopies.get(0).getBookTitleDto().getId());
        Assert.assertEquals("Test title", bookCopies.get(0).getBookTitleDto().getTitle());
        Assert.assertEquals("Test author", bookCopies.get(0).getBookTitleDto().getAuthor());
        Assert.assertEquals(2017, bookCopies.get(0).getBookTitleDto().getIssueDate());
        Assert.assertEquals("available", bookCopies.get(0).getBookStatus().getStatus());
    }
}
