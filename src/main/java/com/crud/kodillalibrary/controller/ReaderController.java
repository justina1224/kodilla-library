package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.controller.exception.ReaderNotFoundException;
import com.crud.kodillalibrary.domain.LibraryReader;
import com.crud.kodillalibrary.domain.LibraryReaderDto;
import com.crud.kodillalibrary.mapper.ReaderMapper;
import com.crud.kodillalibrary.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/reader")
public class ReaderController {
    @Autowired
    private DbService dbService;
    @Autowired
    private ReaderMapper readerMapper;

    @GetMapping
    public List<LibraryReaderDto> getReaders() {
        return readerMapper.mapToReaderDtoList(dbService.getAllReaders());
    }

    @GetMapping(value = "{id}")
    public LibraryReaderDto getReaderById(@PathVariable Long id) throws ReaderNotFoundException {
        return readerMapper.mapToReaderDto(dbService.getReaderById(id)
                .orElseThrow(() -> new ReaderNotFoundException("Reader with id " + id + " doesn't exist")));
    }

    @Transactional
    @DeleteMapping(value = "deleteReader/{id}")
    public void deleteReader(@PathVariable Long id) {
        dbService.deleteReader(id);
    }

    @PutMapping(value = "updateReaderData")
    public LibraryReaderDto updateReaderData(@RequestBody LibraryReaderDto libraryReaderDto) {
        return readerMapper.mapToReaderDto(dbService.saveReader(readerMapper.mapToReader(libraryReaderDto)));
    }

    @PostMapping(value = "/createReader", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody LibraryReaderDto libraryReaderDto) {
        LibraryReader reader = readerMapper.mapToReader(libraryReaderDto);
        reader.setCreatingAccountDate(LocalDate.now());
        dbService.saveReader(reader);
    }
}
