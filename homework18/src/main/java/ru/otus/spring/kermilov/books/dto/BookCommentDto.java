package ru.otus.spring.kermilov.books.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCommentDto {
    private long id;
    private long bookId;
    private String book;
    private String user;
    private String comment;
}
