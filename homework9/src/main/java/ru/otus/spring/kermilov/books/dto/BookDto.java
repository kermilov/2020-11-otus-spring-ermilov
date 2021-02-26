package ru.otus.spring.kermilov.books.dto;

import lombok.*;

import java.util.List;

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
    private List<String> genresList;

    public BookDto(long id, String name, String author, String genres) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genres = genres;
    }
}
