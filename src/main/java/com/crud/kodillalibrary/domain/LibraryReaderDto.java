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
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LibraryReaderDto {
    private Long id;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate creatingAccountDate;

    public LibraryReaderDto(String firstName, String lastName, LocalDate creatingAccountDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.creatingAccountDate = creatingAccountDate;
    }
}
