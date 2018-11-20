package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.controller.exception.BookTitleNotFoundException;
import com.crud.kodillalibrary.domain.BookTitle;
import com.crud.kodillalibrary.domain.BookTitleDto;
import com.crud.kodillalibrary.mapper.BookTitleMapper;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(BookTitleController.class)
public class BookTitleControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookTitleMapper titleMapper;

    @MockBean
    private DbService dbService;

    @Test
    public void shouldFetchListOfTitles() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Title", "Author", 2018);
        List<BookTitle> titles = new ArrayList<>();
        titles.add(bookTitle);
        List<BookTitleDto> titlesDto = new ArrayList<>();
        titlesDto.add(bookTitleDto);

        when(titleMapper.mapToBookTitleDtoList(titles)).thenReturn(titlesDto);
        when(dbService.getAllBookTitles()).thenReturn(titles);

        //When & Then
        mockMvc.perform(get("/v1/book/title")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Title")))
                .andExpect(jsonPath("$[0].author", is("Author")))
                .andExpect(jsonPath("$[0].issueDate", is(2018)));
    }

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        List<BookTitle> titles = new ArrayList<>();
        List<BookTitleDto> titlesDto = new ArrayList<>();

        when(titleMapper.mapToBookTitleDtoList(titles)).thenReturn(titlesDto);
        when(dbService.getAllBookTitles()).thenReturn(titles);

        //When & Then
        mockMvc.perform(get("/v1/book/title")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchBookTitle() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Title", "Author", 2018);

        when(titleMapper.mapToBookTitleDto(bookTitle)).thenReturn(bookTitleDto);
        when(dbService.getBookTitleById(1L)).thenReturn(Optional.of(bookTitle));

        //When & Then
        mockMvc.perform(get("/v1/book/title/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.author", is("Author")))
                .andExpect(jsonPath("$.issueDate", is(2018)));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Test
    public void testGetTitleWhenIdDoesntExist() throws Exception {
        //Given
        when(dbService.getBookTitleById(1L)).thenReturn(Optional.empty());
        expectedException.expectCause(isA(BookTitleNotFoundException.class));

        //When & Then
        mockMvc.perform(get("/v1/book/title/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    public void shouldDeleteBookTitle() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);

        when(dbService.getBookTitleById(1L)).thenReturn(Optional.of(bookTitle));

        //When & Then
        mockMvc.perform(delete("/v1/book/title/deleteBookTitle/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateBookTitle() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Title", "Author", 2018);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookTitleDto);

        when(titleMapper.mapToBookTitle(any())).thenReturn(bookTitle);
        when(dbService.saveBookTitle(bookTitle)).thenReturn(bookTitle);
        when(titleMapper.mapToBookTitleDto(bookTitle)).thenReturn(bookTitleDto);

        //When & Then
        mockMvc.perform(put("/v1/book/title/updateBookTitleData")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.author", is("Author")))
                .andExpect(jsonPath("$.issueDate", is(2018)));
    }

    @Test
    public void shouldCreateBookTitle() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookTitleDto bookTitleDto = new BookTitleDto(1L, "Title", "Author", 2018);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookTitleDto);

        when(titleMapper.mapToBookTitle(bookTitleDto)).thenReturn(bookTitle);

        //When & Then
        mockMvc.perform(post("/v1/book/title/createBookTitle")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
