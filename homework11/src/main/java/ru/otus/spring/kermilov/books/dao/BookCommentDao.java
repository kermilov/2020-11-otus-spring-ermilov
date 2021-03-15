package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kermilov.books.domain.BookComment;

public interface BookCommentDao extends MongoRepository<BookComment, String> {
    void deleteByBookId(String bookId);
}
