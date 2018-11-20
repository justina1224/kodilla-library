package com.crud.kodillalibrary.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "BOOK_TITLES")
@Access(AccessType.FIELD)
public class BookTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "issue_date")
    private int issueDate;

    @JsonBackReference
    @OneToMany(
            targetEntity = BookCopy.class,
            mappedBy = "bookTitle",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<BookCopy> copies = new ArrayList<>();

    public BookTitle(Long id, String title, String author, int issueDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issueDate = issueDate;
    }

    public BookTitle(String title, String author, int issueDate) {
        this.title = title;
        this.author = author;
        this.issueDate = issueDate;
    }
}
