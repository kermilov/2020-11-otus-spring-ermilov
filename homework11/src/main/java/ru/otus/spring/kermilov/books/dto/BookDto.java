package ru.otus.spring.kermilov.books.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private String author;
    private String genres;
}
