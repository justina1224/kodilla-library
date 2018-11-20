package com.crud.kodillalibrary.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@EqualsAndHashCode
public final class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference(value = "reader")
    @JoinColumn(name = "reader_id")
    private LibraryReader reader;

    @ManyToOne
    @JsonBackReference(value = "bookCopy")
    @JoinColumn(name = "copy_id")
    private BookCopy bookCopy;

    @Column(name = "borrowing_date")
    private LocalDate borrowingDate;

    @Column(name = "return_date")
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