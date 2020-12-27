package ru.otus.spring.kermilov.books.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Book {
    private final Long id;
    private final String name;
    private final Long authorID;
    private final Long genreID;

    @Override
    public String toString() {
        return "id = " + id + " " +
               "name = " + name + " " +
               "authorID = " + authorID + " " +
               "genreID = " + genreID;
    }
}
