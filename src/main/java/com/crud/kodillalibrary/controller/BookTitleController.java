package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.controller.exception.BookTitleNotFoundException;
import com.crud.kodillalibrary.domain.BookTitleDto;
import com.crud.kodillalibrary.mapper.BookTitleMapper;
import com.crud.kodillalibrary.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/book/title")
public class BookTitleController {
    @Autowired
    private DbService dbService;
    @Autowired
    private BookTitleMapper bookTitleMapper;

    @GetMapping
    public List<BookTitleDto> getBookTitles() {
        return bookTitleMapper.mapToBookTitleDtoList(dbService.getAllBookTitles());
    }

    @GetMapping(value = "{id}")
    public BookTitleDto getBookTitleById(@PathVariable Long id) throws BookTitleNotFoundException {
        return bookTitleMapper.mapToBookTitleDto(dbService.getBookTitleById(id)
                .orElseThrow(() -> new BookTitleNotFoundException("Book title with id " + id + " doesn't exist")));
    }

    @GetMapping(value = "getBookTitleByTitle/{title}")
    public List<BookTitleDto> getBookTitleByTitle(@PathVariable String title) {
        return bookTitleMapper.mapToBookTitleDtoList(dbService.getBookTitleByTitle(title));
    }

    @Transactional
    @DeleteMapping(value = "deleteBookTitle/{id}")
    public void deleteBookTitle(@PathVariable Long id) {
        dbService.deleteBookTitle(id);
    }

    @PutMapping(value = "updateBookTitleData", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookTitleDto updateBookTitleData(@RequestBody BookTitleDto bookTitleDto) {
        return bookTitleMapper.mapToBookTitleDto(dbService.saveBookTitle(bookTitleMapper.mapToBookTitle(bookTitleDto)));
    }

    @PostMapping(value = "/createBookTitle", consumes = APPLICATION_JSON_VALUE)
    public void createBookTitle(@RequestBody BookTitleDto bookTitleDto) {
        dbService.saveBookTitle(bookTitleMapper.mapToBookTitle(bookTitleDto));
    }
}
