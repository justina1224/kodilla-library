package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.KodillaLibraryApplication;
import com.crud.kodillalibrary.controller.exception.BorrowingNotFoundException;
import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.mapper.BorrowingMapper;
import com.crud.kodillalibrary.service.DbService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.crud.kodillalibrary.KodillaLibraryApplication.FORMATTER;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KodillaLibraryApplication.class, RestTemplateFactory.class})
@WebMvcTest(BorrowingController.class)
public class BorrowingControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingMapper borrowingMapper;

    @MockBean
    private DbService dbService;

    @Autowired
    RestTemplate sut;

    @Test
    public void shouldFetchListOfBorrowings() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.INUSE);
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(1L, reader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30), false);
        BorrowingDto borrowingDto = new BorrowingDto(1L, reader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30), false);
        List<Borrowing> borrowings = new ArrayList<>();
        borrowings.add(borrowing);
        List<BorrowingDto> borrowingsDto = new ArrayList<>();
        borrowingsDto.add(borrowingDto);

        when(borrowingMapper.mapToBorrowingDtoList(borrowings)).thenReturn(borrowingsDto);
        when(dbService.getAllBorrowings()).thenReturn(borrowings);

        //When & Then
        mockMvc.perform(get("/v1/borrowing")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].reader.id", is(1)))
                .andExpect(jsonPath("$[0].reader.firstName", is("Jan")))
                .andExpect(jsonPath("$[0].reader.lastName", is("Kowalski")))
                .andExpect(jsonPath("$[0].reader.creatingAccountDate", is(LocalDate.now().format(FORMATTER))))
                .andExpect(jsonPath("$[0].bookCopy.id", is(1)))
                .andExpect(jsonPath("$[0].bookCopy.bookTitle.title", is("Title")))
                .andExpect(jsonPath("$[0].bookCopy.bookTitle.author", is("Author")))
                .andExpect(jsonPath("$[0].bookCopy.bookTitle.issueDate", is(2018)))
                .andExpect(jsonPath("$[0].bookCopy.bookStatus", is("INUSE")))
                .andExpect(jsonPath("$[0].borrowingDate", is(LocalDate.now().format(FORMATTER))))
                .andExpect(jsonPath("$[0].returnDate", is(LocalDate.now().plusDays(30).format(FORMATTER))));
    }

    @Test
    public void shouldFetchEmptyListOfBorrowings() throws Exception {
        //Given
        List<Borrowing> borrowings = new ArrayList<>();
        List<BorrowingDto> borrowingsDto = new ArrayList<>();

        when(borrowingMapper.mapToBorrowingDtoList(borrowings)).thenReturn(borrowingsDto);
        when(dbService.getAllBorrowings()).thenReturn(borrowings);

        //When & Then
        mockMvc.perform(get("/v1/borrowing")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchBorrowing() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.INUSE);
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(1L, reader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30), false);
        BorrowingDto borrowingDto = new BorrowingDto(1L, reader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30), false);

        when(borrowingMapper.mapToBorrowingDto(borrowing)).thenReturn(borrowingDto);
        when(dbService.getBorrowingById(1L)).thenReturn(Optional.of(borrowing));

        //When & Then
        mockMvc.perform(get("/v1/borrowing/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.reader.id", is(1)))
                .andExpect(jsonPath("$.reader.firstName", is("Jan")))
                .andExpect(jsonPath("$.reader.lastName", is("Kowalski")))
                .andExpect(jsonPath("$.reader.creatingAccountDate", is(LocalDate.now().format(FORMATTER))))
                .andExpect(jsonPath("$.bookCopy.id", is(1)))
                .andExpect(jsonPath("$.bookCopy.bookTitle.title", is("Title")))
                .andExpect(jsonPath("$.bookCopy.bookTitle.author", is("Author")))
                .andExpect(jsonPath("$.bookCopy.bookTitle.issueDate", is(2018)))
                .andExpect(jsonPath("$.bookCopy.bookStatus", is("INUSE")))
                .andExpect(jsonPath("$.borrowingDate", is(LocalDate.now().format(FORMATTER))))
                .andExpect(jsonPath("$.returnDate", is(LocalDate.now().plusDays(30).format(FORMATTER))));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetBorrowingWhenIdDoesntExist() throws Exception {
        //Given
        when(dbService.getBorrowingById(1L)).thenReturn(Optional.empty());
        expectedException.expectCause(isA(BorrowingNotFoundException.class));

        //When & Then
        mockMvc.perform(get("/v1/borrowing/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    public void shouldDeleteBorrowing() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(1L, reader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30), false);

        when(dbService.getBorrowingById(1L)).thenReturn(Optional.of(borrowing));

        //When & Then
        mockMvc.perform(delete("/v1/borrowing/deleteBorrowing/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldBorrow() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L,"Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        BookCopy bookCopy1 = new BookCopy(2L, bookTitle, BookStatus.INUSE);
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(1L, reader, bookCopy1, LocalDate.now(), LocalDate.now().plusDays(30), false);

        when(dbService.getReaderById(1L)).thenReturn(Optional.of(reader));
        when(dbService.getBookTitleById(1L)).thenReturn(Optional.of(bookTitle));
        when(dbService.saveBorrowing(any())).thenReturn(borrowing);

        //When & Then
        mockMvc.perform(post("/v1/borrowing/borrow")
                .param("readerId", "1")
                .param("titleId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldBorrowBack() throws Exception {
        //Given
        BookTitle bookTitle = new BookTitle(1L, "Title", "Author", 2018);
        BookCopy bookCopy = new BookCopy(1L, bookTitle, BookStatus.INUSE);
        BookCopy bookCopy1 = new BookCopy(1L, bookTitle, BookStatus.AVAILABLE);
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        Borrowing borrowing = new Borrowing(1L, reader, bookCopy, LocalDate.now(), LocalDate.now().plusDays(30), false);
        Borrowing borrowing1 = new Borrowing(1L, reader, bookCopy1, LocalDate.now(), LocalDate.now(), true);

        when(dbService.getBookCopyById(1L)).thenReturn(Optional.of(bookCopy));
        when(dbService.getBorrowingById(1L)).thenReturn(Optional.of(borrowing));
        when(dbService.saveBorrowing(any())).thenReturn(borrowing1);

        //When & Then
        mockMvc.perform(put("/v1/borrowing/borrowBack?copyId=1&borrowingId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
