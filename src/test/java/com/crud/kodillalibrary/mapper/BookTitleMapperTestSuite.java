package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.BookTitle;
import com.crud.kodillalibrary.domain.BookTitleDto;
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
public class BookTitleMapperTestSuite {

    @Autowired
    private BookTitleMapper titleMapper;

    @Test
    public void testMapToBookTitle() {
        //Given
        BookTitleDto bookTitledto = new BookTitleDto(1L, "Test title", "Test author", 2018);
        //When
        BookTitle bookTitle = titleMapper.mapToBookTitle(bookTitledto);
        //Then
        Assert.assertNotNull(bookTitle);
        Assert.assertEquals(Long.valueOf("1"), bookTitle.getId());
        Assert.assertEquals("Test title", bookTitle.getTitle());
        Assert.assertEquals("Test author", bookTitle.getAuthor());
        Assert.assertEquals(2018, bookTitle.getIssueDate());
    }

    @Test
    public void testMapToBookTitleDto() {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Test title", "Test author", 2018);
        //When
        BookTitleDto bookTitleDto = titleMapper.mapToBookTitleDto(bookTitle);
        //Then
        Assert.assertNotNull(bookTitleDto);
        Assert.assertEquals(Long.valueOf("1"), bookTitleDto.getId());
        Assert.assertEquals("Test title", bookTitleDto.getTitle());
        Assert.assertEquals("Test author", bookTitleDto.getAuthor());
        Assert.assertEquals(2018, bookTitleDto.getIssueDate());
    }

    @Test
    public void testMapToBookTitleDtoList() {
        //Given
        BookTitle bookTitle1 = new BookTitle(1L, "Test title 1", "Test author 1", 2017);
        BookTitle bookTitle2 = new BookTitle(2L, "Test title 2", "Test author 2", 2018);
        List<BookTitle> titles = new ArrayList<>();
        titles.add(bookTitle1);
        titles.add(bookTitle2);
        //When
        List<BookTitleDto> titlesDto = titleMapper.mapToBookTitleDtoList(titles);
        //Then
        Assert.assertEquals(2, titlesDto.size());
        Assert.assertEquals(Long.valueOf("1"), titlesDto.get(0).getId());
        Assert.assertEquals("Test title 1", titlesDto.get(0).getTitle());
        Assert.assertEquals("Test author 1", titlesDto.get(0).getAuthor());
        Assert.assertEquals(2017, titlesDto.get(0).getIssueDate());
        Assert.assertEquals(Long.valueOf("2"), titlesDto.get(1).getId());
        Assert.assertEquals("Test title 2", titlesDto.get(1).getTitle());
        Assert.assertEquals("Test author 2", titlesDto.get(1).getAuthor());
        Assert.assertEquals(2018, titlesDto.get(1).getIssueDate());
    }
}
