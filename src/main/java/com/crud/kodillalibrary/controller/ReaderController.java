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

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/reader")
public class ReaderController {
    @Autowired
    private DbService dbService;
    @Autowired
    private ReaderMapper readerMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getReaders", produces = APPLICATION_JSON_VALUE)
    public List<LibraryReaderDto> getReaders() {
        return readerMapper.mapToReaderDtoList(dbService.getAllReaders());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getReaderById", produces = APPLICATION_JSON_VALUE)
    public LibraryReaderDto getReaderById(@RequestParam Long id) throws ReaderNotFoundException {
        return readerMapper.mapToReaderDto(dbService.getReaderById(id)
                .orElseThrow(() -> new ReaderNotFoundException("Reader with id " + id + " doesn't exist")));
    }

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "deleteReader")
    public void deleteReader(@RequestParam Long id) {
        dbService.deleteReader(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateReaderData"/*, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE*/)
    public LibraryReaderDto updateReaderData(@RequestBody LibraryReaderDto libraryReaderDto) {
        return readerMapper.mapToReaderDto(dbService.saveReader(readerMapper.mapToReader(libraryReaderDto)));
    }

    @PostMapping(value = "/createReader", consumes = APPLICATION_JSON_VALUE/*, produces = APPLICATION_JSON_VALUE*/)
    public void createReader(@RequestBody LibraryReaderDto libraryReaderDto) {
        LibraryReader reader = readerMapper.mapToReader(libraryReaderDto);
        reader.setCreatingAccountDate(LocalDate.now());
        dbService.saveReader(reader);
    }
}
