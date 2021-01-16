package ru.otus.spring.kermilov.books.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Data
public class Author {
    private final long id;
    private final String name;
}
