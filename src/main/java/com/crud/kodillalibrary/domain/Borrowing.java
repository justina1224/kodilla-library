package com.crud.kodillalibrary.domain;

import com.crud.kodillalibrary.converter.LocalDatePersistenceConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BORROWING_BOOKS")
@Access(AccessType.FIELD)
public final class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "reader_id")
    private LibraryReader reader;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "copy_id")
    private BookCopy bookCopy;

    @Column(name = "borrowing_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate borrowingDate;

    @Column(name = "return_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate returnDate;


    @Column(name = "is_closed")
    private boolean isClosed;

    public Borrowing(Long id, LocalDate borrowingDate, LocalDate returnDate) {
        this.id = id;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public Borrowing(LibraryReader reader, BookCopy bookCopy, LocalDate borrowingDate, LocalDate returnDate) {
        this.reader = reader;
        this.bookCopy = bookCopy;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }
}