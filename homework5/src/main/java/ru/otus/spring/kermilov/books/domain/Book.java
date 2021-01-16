package ru.otus.spring.kermilov.books.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Data
public class Book {
    private final long id;
    private final String name;
    private final Author author;
    private final Genre genre;

}
