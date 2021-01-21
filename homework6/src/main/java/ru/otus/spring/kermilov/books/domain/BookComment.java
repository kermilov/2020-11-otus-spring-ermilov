package ru.otus.spring.kermilov.books.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BookComments") // Задает имя таблицы, на которую будет отображаться сущность
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации идентификаторов
    long id;
    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id", nullable = false)
    Book book;
    @Column(name = "comment", nullable = false)
    String comment;

    @Override
    public String toString() {
        return "BookComment(id=" + this.id + ", comment=" + this.comment + ")";
    }
}
