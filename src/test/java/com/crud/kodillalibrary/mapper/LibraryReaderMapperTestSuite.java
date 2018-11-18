package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.LibraryReader;
import com.crud.kodillalibrary.domain.LibraryReaderDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryReaderMapperTestSuite {

    @Autowired
    private ReaderMapper readerMapper;

    @Test
    public void testMapToReader() {
        //Given
        LibraryReaderDto libraryReaderDto = new LibraryReaderDto(1L, "Jan", "Kowalski", LocalDate.now());
        //When
        LibraryReader reader = readerMapper.mapToReader(libraryReaderDto);
        //Then
        Assert.assertNotNull(reader);
        Assert.assertEquals(Long.valueOf("1"), reader.getId());
        Assert.assertEquals("Jan", reader.getFirstName());
        Assert.assertEquals("Kowalski", reader.getLastName());
        Assert.assertEquals(LocalDate.now(), reader.getCreatingAccountDate());
    }

    @Test
    public void testMapToReaderDto() {
        //Given
        LibraryReader libraryReader = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        //When
        LibraryReaderDto readerDto = readerMapper.mapToReaderDto(libraryReader);
        //Then
        Assert.assertNotNull(readerDto);
        Assert.assertEquals(Long.valueOf("1"), readerDto.getId());
        Assert.assertEquals("Jan", readerDto.getFirstName());
        Assert.assertEquals("Kowalski", readerDto.getLastName());
        Assert.assertEquals(LocalDate.now(), readerDto.getCreatingAccountDate());
    }

    @Test
    public void testMapToReaderDtoList() {
        //Given
        LibraryReader libraryReader1 = new LibraryReader(1L, "Jan", "Kowalski", LocalDate.now());
        LibraryReader libraryReader2 = new LibraryReader(2L, "Jolanta", "Grela", LocalDate.now().minusDays(2));
        List<LibraryReader> readers = new ArrayList<>();
        readers.add(libraryReader1);
        readers.add(libraryReader2);
        //When
        List<LibraryReaderDto> readersDto = readerMapper.mapToReaderDtoList(readers);
        //Then
        Assert.assertEquals(2, readersDto.size());
        Assert.assertEquals(Long.valueOf("1"), readersDto.get(0).getId());
        Assert.assertEquals("Jan", readersDto.get(0).getFirstName());
        Assert.assertEquals("Kowalski", readersDto.get(0).getLastName());
        Assert.assertEquals(LocalDate.now(), readersDto.get(0).getCreatingAccountDate());
        Assert.assertEquals(Long.valueOf("2"), readersDto.get(1).getId());
        Assert.assertEquals("Jolanta", readersDto.get(1).getFirstName());
        Assert.assertEquals("Grela", readersDto.get(1).getLastName());
        Assert.assertEquals(LocalDate.now().minusDays(2), readersDto.get(1).getCreatingAccountDate());
    }
}
