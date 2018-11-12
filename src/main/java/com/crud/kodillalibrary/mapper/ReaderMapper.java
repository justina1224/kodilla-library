package com.crud.kodillalibrary.mapper;

import com.crud.kodillalibrary.domain.LibraryReader;
import com.crud.kodillalibrary.domain.LibraryReaderDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {
    public LibraryReader mapToReader(final LibraryReaderDto libraryReaderDto) {
        return new LibraryReader(
                libraryReaderDto.getId(),
                libraryReaderDto.getFirstName(),
                libraryReaderDto.getLastName(),
                libraryReaderDto.getCreatingAccountDate());
    }

    public LibraryReaderDto mapToReaderDto(final LibraryReader libraryReader) {
        return new LibraryReaderDto(
                libraryReader.getId(),
                libraryReader.getFirstName(),
                libraryReader.getLastName(),
                libraryReader.getCreatingAccountDate());
    }

    public List<LibraryReaderDto> mapToReaderDtoList(final List<LibraryReader> readersList) {
        return readersList.stream()
                .map(t -> new LibraryReaderDto(t.getId(), t.getFirstName(), t.getLastName(), t.getCreatingAccountDate()))
                .collect(Collectors.toList());
    }
}
