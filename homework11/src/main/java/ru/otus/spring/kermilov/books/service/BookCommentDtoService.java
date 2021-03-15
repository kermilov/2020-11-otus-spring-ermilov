package ru.otus.spring.kermilov.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.kermilov.books.dao.BookCommentReactDao;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.BookComment;
import ru.otus.spring.kermilov.books.dto.BookCommentDto;

@Service
@RequiredArgsConstructor
public class BookCommentDtoService {
    private final BookCommentReactDao bookCommentReactDao;

    @Transactional(readOnly=true)
    public Flux<BookCommentDto> getByBookId(String id) {
        return bookCommentReactDao.getByBookId(id).map(this::getBookCommentDto);
    }

    @Transactional(readOnly=true)
    public Mono<BookCommentDto> getBookCommentById(String id) {
        return bookCommentReactDao.findById(id).map(this::getBookCommentDto);
    }

    @Transactional
    public Mono<BookCommentDto> save(BookCommentDto bookCommentDto) {
        return bookCommentReactDao.save(getBookComment(bookCommentDto)).map(this::getBookCommentDto);
    }

    @Transactional
    public Mono<Void> deleteById(String id) {
        return bookCommentReactDao.deleteById(id);
    }

    private BookComment getBookComment(BookCommentDto bookCommentDto) {
        BookComment bookComment = new BookComment();
        bookComment.setId(bookCommentDto.getId());
        bookComment.setBook(new Book(bookCommentDto.getBookId()));
        bookComment.setComment(bookCommentDto.getComment());
        return bookComment;
    }

    private BookCommentDto getBookCommentDto(BookComment bookComment) {
        BookCommentDto bookCommentDto = new BookCommentDto();
        bookCommentDto.setId(bookComment.getId());
        bookCommentDto.setBookId(bookComment.getBook().getId());
        bookCommentDto.setBook(bookComment.getBook().getName());
        bookCommentDto.setComment(bookComment.getComment());
        return bookCommentDto;
    }
}
