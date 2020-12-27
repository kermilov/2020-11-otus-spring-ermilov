package ru.otus.spring.kermilov.books.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Genre {
    private final Long id;
    private final String name;
}
