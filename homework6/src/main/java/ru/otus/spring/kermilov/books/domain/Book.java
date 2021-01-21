package ru.otus.spring.kermilov.books.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Books") // Задает имя таблицы, на которую будет отображаться сущность
@NamedEntityGraph(name = "books-author-entity-graph",
        attributeNodes = {@NamedAttributeNode("author")})
public class Book {
    @Id // Позволяет указать какое поле является идентификатором
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации идентификаторов
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_author_id"))
    private Author author;
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "book_id", insertable = false)
    private List<BookComment> comments;


    public Book(long id, String name, Author author, List<Genre> genres) {
        super();
        this.setId(id);
        this.setName(name);
        this.setAuthor(author);
        this.setGenres(genres);
    }
}
