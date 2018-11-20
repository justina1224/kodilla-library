package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.KodillaLibraryApplication;
import com.crud.kodillalibrary.controller.exception.ReaderNotFoundException;
import com.crud.kodillalibrary.domain.LibraryReader;
import com.crud.kodillalibrary.domain.LibraryReaderDto;
import com.crud.kodillalibrary.jsonformatting.LocalDateAdapter;
import com.crud.kodillalibrary.mapper.ReaderMapper;
import com.crud.kodillalibrary.service.DbService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
@WebMvcTest(ReaderController.class)
public class ReaderControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReaderMapper readerMapper;

    @MockBean
    private DbService dbService;

    @Autowired
    RestTemplate sut;

    @Test
    public void shouldFetchListOfReaders() throws Exception {
        //Given
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        LibraryReaderDto readerDto = new LibraryReaderDto(1L, "Jan", "Kowalski", LocalDate.now());
        List<LibraryReader> readers = new ArrayList<>();
        readers.add(reader);
        List<LibraryReaderDto> readersDto = new ArrayList<>();
        readersDto.add(readerDto);

        when(readerMapper.mapToReaderDtoList(readers)).thenReturn(readersDto);
        when(dbService.getAllReaders()).thenReturn(readers);

        //When & Then
        mockMvc.perform(get("/v1/reader")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Jan")))
                .andExpect(jsonPath("$[0].lastName", is("Kowalski")))
                .andExpect(jsonPath("$[0].creatingAccountDate", is(LocalDate.now().format(FORMATTER))));
    }

    @Test
    public void shouldFetchEmptyListOfReaders() throws Exception {
        //Given
        List<LibraryReader> readers = new ArrayList<>();
        List<LibraryReaderDto> readersDto = new ArrayList<>();

        when(readerMapper.mapToReaderDtoList(readers)).thenReturn(readersDto);
        when(dbService.getAllReaders()).thenReturn(readers);

        //When & Then
        mockMvc.perform(get("/v1/reader")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchLibraryReader() throws Exception {
        //Given
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        LibraryReaderDto readerDto = new LibraryReaderDto(1L, "Jan", "Kowalski", LocalDate.now());

        when(readerMapper.mapToReaderDto(reader)).thenReturn(readerDto);
        when(dbService.getReaderById(1L)).thenReturn(Optional.of(reader));

        //When & Then
        mockMvc.perform(get("/v1/reader/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Jan")))
                .andExpect(jsonPath("$.lastName", is("Kowalski")))
                .andExpect(jsonPath("$.creatingAccountDate", is(LocalDate.now().format(FORMATTER))));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Test
    public void testGetReaderWhenIdDoesntExist() throws Exception {
        //Given
        when(dbService.getReaderById(1L)).thenReturn(Optional.empty());
        expectedException.expectCause(isA(ReaderNotFoundException.class));

        //When & Then
        mockMvc.perform(get("/v1/reader/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    public void shouldDeleteReader() throws Exception {
        //Given
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());

        when(dbService.getReaderById(1L)).thenReturn(Optional.of(reader));

        //When & Then
        mockMvc.perform(delete("/v1/reader/deleteReader/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateReaderData() throws Exception {
        //Given
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        LibraryReaderDto readerDto = new LibraryReaderDto(1L, "Jan", "Kowalski", LocalDate.now());

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(readerDto);

        when(readerMapper.mapToReader(any())).thenReturn(reader);
        when(dbService.saveReader(reader)).thenReturn(reader);
        when(readerMapper.mapToReaderDto(reader)).thenReturn(readerDto);

        //When & Then
        mockMvc.perform(put("/v1/reader/updateReaderData")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Jan")))
                .andExpect(jsonPath("$.lastName", is("Kowalski")))
                .andExpect(jsonPath("$.creatingAccountDate", is(LocalDate.now().format(FORMATTER))));
    }

    @Test
    public void shouldCreateReader() throws Exception {
        //Given
        LibraryReader reader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        LibraryReaderDto readerDto = new LibraryReaderDto(1L, "Jan", "Kowalski", LocalDate.now());

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(readerDto);

        when(readerMapper.mapToReader(readerDto)).thenReturn(reader);

        //When & Then
        mockMvc.perform(post("/v1/reader/createReader")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
