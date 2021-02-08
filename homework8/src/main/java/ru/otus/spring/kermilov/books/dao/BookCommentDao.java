package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.kermilov.books.domain.BookComment;

import java.util.List;

public interface BookCommentDao extends MongoRepository<BookComment, String> {
    List<BookComment> getByBookId(String bookId);
    void deleteByBookId(String bookId);
}
