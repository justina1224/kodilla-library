package com.crud.kodillalibrary.dao;

import com.crud.kodillalibrary.domain.LibraryReader;
import com.crud.kodillalibrary.repository.ReaderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryReaderRepositoryTestSuite {
    @Autowired
    private ReaderRepository readerRepository;

    @Test
    public void testSaveReader() {
        //Given
        LibraryReader libraryReader = new LibraryReader("Jan", "Kowalski", LocalDate.now());

        //When
        readerRepository.save(libraryReader);
        Long id = libraryReader.getId();
        LibraryReader reader = readerRepository.findOne(id);

        //Then

        Assert.assertEquals(id, reader.getId());
        Assert.assertEquals("Jan", reader.getFirstName());
        Assert.assertEquals("Kowalski", reader.getLastName());
        Assert.assertEquals(LocalDate.now(), reader.getCreatingAccountDate());

        //CleanUp
        readerRepository.delete(id);
    }

    @Test
    public void testFindAllReaders() {
        //Given
        LibraryReader libraryReader1 = new LibraryReader("Jan", "Kowalski", LocalDate.now());
        LibraryReader libraryReader2 = new LibraryReader("Joanna", "Zalewska", LocalDate.now().minusDays(2));


        //When
        readerRepository.save(libraryReader1);
        readerRepository.save(libraryReader2);

        Long id1 = libraryReader1.getId();
        Long id2 = libraryReader2.getId();

        List<LibraryReader> readers = readerRepository.findAll();

        //Then
        Assert.assertEquals(2, readers.size());
        Assert.assertEquals(id1, readers.get(0).getId());
        Assert.assertEquals("Jan", readers.get(0).getFirstName());
        Assert.assertEquals("Kowalski", readers.get(0).getLastName());
        Assert.assertEquals(LocalDate.now(), readers.get(0).getCreatingAccountDate());
        Assert.assertEquals(id2, readers.get(1).getId());
        Assert.assertEquals("Joanna", readers.get(1).getFirstName());
        Assert.assertEquals("Zalewska", readers.get(1).getLastName());
        Assert.assertEquals(LocalDate.now().minusDays(2), readers.get(1).getCreatingAccountDate());

        //CleanUp
        readerRepository.delete(id1);
        readerRepository.delete(id2);
    }

    @Test
    public void testFindReaderById() {
        //Given
        LibraryReader libraryReader = new LibraryReader("Jan", "Kowalski", LocalDate.now());

        //When
        readerRepository.save(libraryReader);
        Long id = libraryReader.getId();
        LibraryReader reader = readerRepository.findById(id).orElse(new LibraryReader());

        //Then
        Assert.assertEquals(id, reader.getId());
        Assert.assertEquals("Jan", reader.getFirstName());
        Assert.assertEquals("Kowalski", reader.getLastName());
        Assert.assertEquals(LocalDate.now(), reader.getCreatingAccountDate());

        //CleanUp
        readerRepository.delete(id);
    }

    @Test
    public void testDeleteReaderById() {
        //Given
        LibraryReader libraryReader = new LibraryReader("Jan", "Kowalski", LocalDate.now());

        //When
        readerRepository.save(libraryReader);
        Long id = libraryReader.getId();
        readerRepository.deleteById(id);

        List<LibraryReader> readers = readerRepository.findAll();

        //Then
        Assert.assertEquals(0, readers.size());
    }
}
