package com.crud.kodillalibrary.bookTitleDao;

import com.crud.kodillalibrary.domain.BookTitle;
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
public class BookTitleRepositoryTestSuite {

    @Autowired
    private BookTitleRepository repository;

    @Test
    public void testSaveBookTitle() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);

        //When
        repository.save(bookTitle);
        Long id = bookTitle.getId();
        BookTitle savedTitle = repository.findOne(id);

        //Then
        Assert.assertEquals(id, savedTitle.getId());
        Assert.assertEquals("Nieodnaleziona", bookTitle.getTitle());
        Assert.assertEquals("Remigiusz Mroz", bookTitle.getAuthor());
        Assert.assertEquals(2018, bookTitle.getIssueDate());

        //Cleanup
        repository.deleteById(id);
    }

    @Test
    public void testfindAllBookTitles() {
        //Given
        BookTitle bookTitle1 = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);
        BookTitle bookTitle2 = new BookTitle("Hashtag", "Remigiusz Mroz", 2017);

        //When
        repository.save(bookTitle1);
        Long id1 = bookTitle1.getId();
        repository.save(bookTitle2);
        Long id2 = bookTitle2.getId();

        List<BookTitle> allBooks = repository.findAll();

        //Then
        Assert.assertEquals(2, allBooks.size());
        Assert.assertEquals(id1, allBooks.get(0).getId());
        Assert.assertEquals("Nieodnaleziona", allBooks.get(0).getTitle());
        Assert.assertEquals("Remigiusz Mroz", allBooks.get(0).getAuthor());
        Assert.assertEquals(2018, allBooks.get(0).getIssueDate());
        Assert.assertEquals(id2, allBooks.get(1).getId());
        Assert.assertEquals("Hashtag", allBooks.get(1).getTitle());
        Assert.assertEquals("Remigiusz Mroz", allBooks.get(1).getAuthor());
        Assert.assertEquals(2017, allBooks.get(1).getIssueDate());

        //Cleanup
        repository.deleteById(id1);
        repository.deleteById(id2);
    }

    @Test
    public void testFindTitleById() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);

        //When
        repository.save(bookTitle);
        Long id = bookTitle.getId();
        BookTitle savedTitle = repository.findById(id).orElse(new BookTitle("title", "author", 2000));

        //Then
        Assert.assertEquals(id, savedTitle.getId());
        Assert.assertEquals("Nieodnaleziona", savedTitle.getTitle());
        Assert.assertEquals("Remigiusz Mroz", savedTitle.getAuthor());
        Assert.assertEquals(2018, savedTitle.getIssueDate());

        //Cleanup
        repository.deleteById(id);
    }

    @Test
    public void testFindTitleByTitle() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);

        //When
        repository.save(bookTitle);
        String title = bookTitle.getTitle();
        Long id = bookTitle.getId();
        List<BookTitle> titles = repository.findByTitle(title);

        //Then
        Assert.assertEquals(1, titles.size());
        Assert.assertEquals(id, titles.get(0).getId());
        Assert.assertEquals("Nieodnaleziona", titles.get(0).getTitle());
        Assert.assertEquals("Remigiusz Mroz", titles.get(0).getAuthor());
        Assert.assertEquals(2018, titles.get(0).getIssueDate());

        //Cleanup
        repository.deleteById(id);
    }

    @Test
    public void testDeleteById() {
        //Given
        BookTitle bookTitle = new BookTitle("Nieodnaleziona", "Remigiusz Mroz", 2018);

        //When
        repository.save(bookTitle);
        Long id = bookTitle.getId();
        repository.deleteById(id);
        List<BookTitle> titles = repository.findAll();

        //Then
        Assert.assertEquals(0, titles.size());
    }
}
