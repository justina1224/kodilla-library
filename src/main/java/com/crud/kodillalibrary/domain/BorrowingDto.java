package com.crud.kodillalibrary.domain;

import com.crud.kodillalibrary.converter.LocalDatePersistenceConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingDto {
    private Long id;
    private LibraryReader reader;
    private BookCopy bookCopy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate borrowingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate returnDate;
    private boolean isClosed = false;

    public BorrowingDto(LibraryReader reader, BookCopy bookCopy, LocalDate borrowingDate, LocalDate returnDate, boolean isClosed) {
        this.reader = reader;
        this.bookCopy = bookCopy;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.isClosed = isClosed;
    }
}
