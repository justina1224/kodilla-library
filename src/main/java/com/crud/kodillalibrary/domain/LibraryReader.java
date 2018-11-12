package com.crud.kodillalibrary.domain;

import com.crud.kodillalibrary.converter.LocalDatePersistenceConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "READERS")
@Access(AccessType.FIELD)
public class LibraryReader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "creating_account_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate creatingAccountDate;

    @JsonManagedReference
    @OneToMany(targetEntity = Borrowing.class,
            mappedBy = "reader",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Borrowing> borrowedBooks = new ArrayList<>();

    public LibraryReader(String firstName, String lastName, LocalDate creatingAccountDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.creatingAccountDate = creatingAccountDate;
    }

    public LibraryReader(Long id, String firstName, String lastName, LocalDate creatingAccountDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creatingAccountDate = creatingAccountDate;
    }
}
