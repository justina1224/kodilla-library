package com.crud.kodillalibrary.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BookTitleDto {
    private Long id;
    private String title;
    private String author;
    private int issueDate;

    public BookTitleDto(String title, String author, int issueDate) {
        this.title = title;
        this.author = author;
        this.issueDate = issueDate;
    }
}
