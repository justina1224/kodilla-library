package com.crud.kodillalibrary.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "BOOK_COPIES")
@Access(AccessType.FIELD)
public class BookCopy {
    public static final String AVAILABLE = "available";
    public static final String IN_USE = "inUse";
    public static final String DAMAGED = "damaged";
    public static final String LOST = "lost";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "title_id")
    private BookTitle bookTitle;

    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @JsonManagedReference(value = "bookCopy")
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            targetEntity = Borrowing.class,
            mappedBy = "bookCopy"
    )
    private List<Borrowing> borrowings;

    public BookCopy(BookTitle bookTitle, BookStatus bookStatus) {
        this.bookTitle = bookTitle;
        this.bookStatus = bookStatus;
    }

    public BookCopy(Long id, BookTitle bookTitle, BookStatus bookStatus) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookStatus = bookStatus;
    }
}