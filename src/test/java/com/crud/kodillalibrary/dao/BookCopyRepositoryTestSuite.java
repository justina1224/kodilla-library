package com.crud.kodillalibrary.dao;

import com.crud.kodillalibrary.domain.BookCopy;
import com.crud.kodillalibrary.domain.BookStatus;
import com.crud.kodillalibrary.domain.BookTitle;
import com.crud.kodillalibrary.repository.BookCopyRepository;
import com.crud.kodillalibrary.repository.BookTitleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookCopyRepositoryTestSuite {
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookTitleRepository bookTitleRepository;

    @Test
    public void testSaveBookTitle() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookCopy bookCopy = new BookCopy(bookTitle, BookStatus.AVAILABLE);

        //When
        bookTitleRepository.save(bookTitle);
        bookCopyRepository.save(bookCopy);
        Long titleId = bookTitle.getId();
        Long copyId = bookCopy.getId();
        BookCopy savedCopy = bookCopyRepository.findOne(copyId);

        //Then
        Assert.assertEquals(copyId, savedCopy.getId());
        Assert.assertEquals("Nieodnaleziona", savedCopy.getBookTitle().getTitle());
        Assert.assertEquals(BookStatus.AVAILABLE, savedCopy.getBookStatus());

        //Cleanup
        bookTitleRepository.deleteById(titleId);
        bookCopyRepository.deleteById(copyId);
    }

    @Test
    public void testfindAllBookCopies() {
        //Given
        BookTitle bookTitle1 = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookTitle bookTitle2 = new BookTitle("Hashtag", "Remigiusz Mroz", 2017);
        BookCopy bookCopy1 = new BookCopy(bookTitle1, BookStatus.AVAILABLE);
        BookCopy bookCopy2 = new BookCopy(bookTitle2, BookStatus.AVAILABLE);

        //When
        bookTitleRepository.save(bookTitle1);
        bookCopyRepository.save(bookCopy1);
        Long titleId1 = bookTitle1.getId();
        Long copyId1 = bookCopy1.getId();
        bookTitleRepository.save(bookTitle2);
        bookCopyRepository.save(bookCopy2);
        Long titleId2 = bookTitle2.getId();
        Long copyId2 = bookCopy2.getId();

        List<BookCopy> allCopies = bookCopyRepository.findAll();

        //Then
        Assert.assertEquals(2, allCopies.size());
        Assert.assertEquals(copyId1, allCopies.get(0).getId());
        Assert.assertEquals("Nieodnaleziona", allCopies.get(0).getBookTitle().getTitle());
        Assert.assertEquals(BookStatus.AVAILABLE, allCopies.get(0).getBookStatus());
        Assert.assertEquals(copyId2, allCopies.get(1).getId());
        Assert.assertEquals("Hashtag", allCopies.get(1).getBookTitle().getTitle());
        Assert.assertEquals(BookStatus.AVAILABLE, allCopies.get(1).getBookStatus());

        //Cleanup
        bookTitleRepository.deleteById(titleId1);
        bookCopyRepository.deleteById(copyId1);
        bookTitleRepository.deleteById(titleId2);
        bookCopyRepository.deleteById(copyId2);
    }

    @Test
    public void testFindCopyById() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookCopy bookCopy = new BookCopy(bookTitle, BookStatus.AVAILABLE);

        //When
        bookTitleRepository.save(bookTitle);
        bookCopyRepository.save(bookCopy);
        Long titleId = bookTitle.getId();
        Long copyId = bookCopy.getId();
        BookCopy savedCopy = bookCopyRepository.findById(copyId).orElse(new BookCopy());

        //Then
        Assert.assertEquals(copyId, savedCopy.getId());
        Assert.assertEquals("Nieodnaleziona", savedCopy.getBookTitle().getTitle());
        Assert.assertEquals(BookStatus.AVAILABLE, savedCopy.getBookStatus());

        //Cleanup
        bookTitleRepository.deleteById(titleId);
        bookCopyRepository.deleteById(copyId);
    }

    @Test
    public void testFindBookCopyByStatus() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookCopy bookCopy1 = new BookCopy(bookTitle, BookStatus.AVAILABLE);
        BookCopy bookCopy2 = new BookCopy(bookTitle, BookStatus.DAMAGED);
        BookCopy bookCopy3 = new BookCopy(bookTitle, BookStatus.AVAILABLE);

        //When
        bookTitleRepository.save(bookTitle);
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
        bookCopyRepository.save(bookCopy3);
        Long titleId = bookTitle.getId();
        Long copyId1 = bookCopy1.getId();
        Long copyId2 = bookCopy2.getId();
        Long copyId3 = bookCopy3.getId();
        List<BookCopy> copies = bookCopyRepository.findByBookStatus(BookStatus.AVAILABLE);

        //Then
        Assert.assertEquals(2, copies.size());
        Assert.assertEquals(copyId1, copies.get(0).getId());
        Assert.assertEquals("Nieodnaleziona", copies.get(0).getBookTitle().getTitle());
        Assert.assertEquals("available", copies.get(0).getBookStatus().getStatus());
        Assert.assertEquals(copyId3, copies.get(1).getId());
        Assert.assertEquals("Nieodnaleziona", copies.get(1).getBookTitle().getTitle());
        Assert.assertEquals("available", copies.get(1).getBookStatus().getStatus());

        //Cleanup
        bookTitleRepository.deleteById(titleId);
        bookCopyRepository.deleteById(copyId1);
        bookCopyRepository.deleteById(copyId2);
        bookCopyRepository.deleteById(copyId3);
    }

    @Test
    public void testDeleteCopyById() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookCopy bookCopy = new BookCopy(bookTitle, BookStatus.AVAILABLE);

        //When
        bookTitleRepository.save(bookTitle);
        Long titleId = bookTitle.getId();
        bookCopyRepository.save(bookCopy);
        Long copyId = bookCopy.getId();
        bookCopyRepository.deleteById(copyId);

        List<BookCopy> copies = bookCopyRepository.findAll();

        //Then
        Assert.assertEquals(0, copies.size());

        //Clean up
        bookTitleRepository.deleteById(titleId);
    }
}
