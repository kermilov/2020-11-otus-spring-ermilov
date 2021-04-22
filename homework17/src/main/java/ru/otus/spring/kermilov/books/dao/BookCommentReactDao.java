package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.kermilov.books.domain.BookComment;

public interface BookCommentReactDao extends ReactiveMongoRepository<BookComment, String> {
    Flux<BookComment> getByBookId(String bookId);
}
