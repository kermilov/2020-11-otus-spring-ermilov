package ru.otus.spring.kermilov.books.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    private String name;
    private String author;
    private String genres;
}
