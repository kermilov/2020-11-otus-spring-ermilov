package ru.otus.spring.kermilov.books.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "BookComments") // Задает имя таблицы, на которую будет отображаться сущность
public class BookComment {
    @Id
    private String id;
    @Field(name = "book_id")
    @DBRef
    private Book book;
    @Field
    private String comment;

    public BookComment(Book book, String comment) {
        this.book = book;
        this.comment = comment;
    }
}
