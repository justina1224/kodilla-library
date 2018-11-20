package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.KodillaLibraryApplication;
import com.crud.kodillalibrary.controller.exception.BookCopyNotFoundException;
import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.mapper.BookCopyMapper;
import com.crud.kodillalibrary.service.DbService;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KodillaLibraryApplication.class, RestTemplateFactory.class})
@WebMvcTest(BookCopyController.class)
public class BookCopyControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookCopyMapper copyMapper;

    @MockBean
    private DbService dbService;

    @Autowired
    RestTemplate sut;

    @Test
    public void shouldFetchListOfCopies() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        BookCopyDto bookCopyDto = new BookCopyDto(1L, bookTitleDto, BookStatus.AVAILABLE);
        List<BookCopy> copies = new ArrayList<>();
        copies.add(bookCopy);
        List<BookCopyDto> copiesDto = new ArrayList<>();
        copiesDto.add(bookCopyDto);

        when(copyMapper.mapToBookCopyDtoList(copies)).thenReturn(copiesDto);
        when(dbService.getAllBookCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/v1/book/copy")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].bookTitleDto.id", is(1)))
                .andExpect(jsonPath("$[0].bookTitleDto.title", is("Title")))
                .andExpect(jsonPath("$[0].bookTitleDto.author", is("Author")))
                .andExpect(jsonPath("$[0].bookTitleDto.issueDate", is(2018)))
                .andExpect(jsonPath("$[0].bookStatus", is("AVAILABLE")));
    }

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        List<BookCopy> copies = new ArrayList<>();
        List<BookCopyDto> copiesDto = new ArrayList<>();

        when(copyMapper.mapToBookCopyDtoList(copies)).thenReturn(copiesDto);
        when(dbService.getAllBookCopies()).thenReturn(copies);

        //When & Then
        mockMvc.perform(get("/v1/book/copy")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchBookCopy() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        BookCopyDto bookCopyDto = new BookCopyDto(1L, bookTitleDto, BookStatus.AVAILABLE);

        when(copyMapper.mapToBookCopyDto(bookCopy)).thenReturn(bookCopyDto);
        when(dbService.getBookCopyById(1L)).thenReturn(Optional.of(bookCopy));

        //When & Then
        mockMvc.perform(get("/v1/book/copy/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.bookTitleDto.id", is(1)))
                .andExpect(jsonPath("$.bookTitleDto.title", is("Title")))
                .andExpect(jsonPath("$.bookTitleDto.author", is("Author")))
                .andExpect(jsonPath("$.bookTitleDto.issueDate", is(2018)))
                .andExpect(jsonPath("$.bookStatus", is("AVAILABLE")));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Test
    public void testGetBookCopyWhenIdDoesntExist() throws Exception {
        //Given
        when(dbService.getBookCopyById(1L)).thenReturn(Optional.empty());
        expectedException.expectCause(isA(BookCopyNotFoundException.class));

        //When & Then
        mockMvc.perform(get("/v1/book/copy/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    public void shouldDeleteBookCopy() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);

        when(dbService.getBookCopyById(1L)).thenReturn(Optional.of(bookCopy));

        //When & Then
        mockMvc.perform(delete("/v1/book/copy/deleteBookCopy/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldChangeBookCopyStatus() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        BookCopy bookCopy1 = new BookCopy(1L, bookTitle, BookStatus.DAMAGED);

        when(dbService.getBookCopyById(1L)).thenReturn(Optional.of(bookCopy));
        when(dbService.saveBookCopy(any())).thenReturn(bookCopy1);

        //When & Then
        mockMvc.perform(put("/v1/book/copy/changeStatus?id=1&bookStatus=damaged")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateBookCopy() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        BookCopyDto bookCopyDto = new BookCopyDto(1L, bookTitleDto, BookStatus.AVAILABLE);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookCopyDto);

        when(copyMapper.mapToBookCopy(bookCopyDto)).thenReturn(bookCopy);
        when(dbService.saveBookCopy(bookCopy)).thenReturn(bookCopy);

        //When & Then
        mockMvc.perform(post("/v1/book/copy/createBookCopy")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateBookCopyByTitle() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        BookCopyDto bookCopyDto = new BookCopyDto(1L, bookTitleDto, BookStatus.AVAILABLE);

        when(dbService.getBookTitleById(1L)).thenReturn(Optional.of(bookTitle));
        when(copyMapper.mapToBookCopyByTitle(bookTitle)).thenReturn(bookCopy);
        when(dbService.saveBookCopy(bookCopy)).thenReturn(bookCopy);

        //When & Then
        mockMvc.perform(post("/v1/book/copy/createBookCopyByTitle/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldShowAvailableCopies() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Title", "Author", 2018);
        BookCopyDto bookCopyDto = new BookCopyDto(1L, bookTitleDto, BookStatus.AVAILABLE);
        List<BookCopyDto> copiesDto = new ArrayList<>();
        copiesDto.add(bookCopyDto);

        when(dbService.getBookTitleById(1L)).thenReturn(Optional.of(bookTitle));
        when(copyMapper.mapToBookCopyDtoList(any())).thenReturn(copiesDto);

        //When & Then
        mockMvc.perform(get("/v1/book/copy/showAvailable/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].bookTitleDto.id", is(1)))
                .andExpect(jsonPath("$[0].bookTitleDto.title", is("Title")))
                .andExpect(jsonPath("$[0].bookTitleDto.author", is("Author")))
                .andExpect(jsonPath("$[0].bookTitleDto.issueDate", is(2018)))
                .andExpect(jsonPath("$[0].bookStatus", is("AVAILABLE")));
    }
}
