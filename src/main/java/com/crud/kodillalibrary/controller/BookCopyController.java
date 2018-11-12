package com.crud.kodillalibrary.controller;

import com.crud.kodillalibrary.controller.exception.BookCopyNotFoundException;
import com.crud.kodillalibrary.controller.exception.BookTitleNotFoundException;
import com.crud.kodillalibrary.domain.*;
import com.crud.kodillalibrary.mapper.BookCopyMapper;
import com.crud.kodillalibrary.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/book/copy")
public class BookCopyController {
    @Autowired
    private DbService dbService;
    @Autowired
    private BookCopyMapper bookCopyMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getBookCopies")
    public List<BookCopyDto> getBookCopies() {
        return bookCopyMapper.mapToBookCopyDtoList(dbService.getAllBookCopies());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBookCopyById")
    public BookCopyDto getBookCopyById(@RequestParam Long id) throws BookCopyNotFoundException {
        return bookCopyMapper.mapToBookCopyDto(dbService.getBookCopyById(id)
                .orElseThrow(() -> new BookCopyNotFoundException("Book copy with id " + id + " doesn't exist")));
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBookCopyByStatus/{bookStatus}")
    public List<BookCopyDto> getBookCopyByStatus(@PathVariable String bookStatus) {
        List<BookCopy> copies = dbService.getAllBookCopies().stream()
                .filter(c -> c.getBookStatus().getStatus().equalsIgnoreCase(bookStatus))
                .collect(Collectors.toList());
        return bookCopyMapper.mapToBookCopyDtoList(copies);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "deleteBookCopy")
    public void deleteBookCopy(@RequestParam Long id) {
        dbService.deleteBookCopy(id);
    }


    @PostMapping(value = "/createBookCopy", consumes = APPLICATION_JSON_VALUE)
    public void createBookCopy(@RequestBody BookCopyDto bookCopyDto) {
        dbService.saveBookCopy(bookCopyMapper.mapToBookCopy(bookCopyDto));
    }

    @PutMapping(value = "/changeStatus")
    public void changeBookCopyStatus(@RequestParam Long id, String bookStatus) throws BookCopyNotFoundException {
        BookCopy copy  = dbService.getBookCopyById(id)
                .orElseThrow(() -> new BookCopyNotFoundException("Book copy with id " + id + " doesn't exist"));
        copy.setBookStatus(BookStatus.valueOf(bookStatus.toUpperCase()));
        dbService.saveBookCopy(copy);

    }

    @GetMapping(value = "/showAvailable")
    public List<BookCopyDto> showAvailableCopies(@RequestParam Long titleId) throws BookTitleNotFoundException {
        BookTitle bookTitle = dbService.getBookTitleById(titleId)
                .orElseThrow(() -> new BookTitleNotFoundException("Book title with id " + titleId + " doesn't exist"));
        List<BookCopy> copies = bookTitle.getCopies().stream()
                .filter(t -> t.getBookStatus().getStatus().equalsIgnoreCase(("available")))
                .collect(Collectors.toList());
        return bookCopyMapper.mapToBookCopyDtoList(copies);
    }

    @PostMapping(value = "/createBookCopyByTitle")
    public void createBookCopy(@RequestParam Long id) throws BookTitleNotFoundException{
        BookTitle bookTitle = dbService.getBookTitleById(id)
                .orElseThrow(() -> new BookTitleNotFoundException("Book title with id " + id + " doesn't exist"));
        dbService.saveBookCopy(bookCopyMapper.mapToBookCopyByTitle(bookTitle));
    }
}
