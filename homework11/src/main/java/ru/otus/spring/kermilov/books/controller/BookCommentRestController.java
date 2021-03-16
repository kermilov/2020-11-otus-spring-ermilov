package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.kermilov.books.dao.BookCommentReactDao;
import ru.otus.spring.kermilov.books.dto.BookCommentDto;
import ru.otus.spring.kermilov.books.transform.BookCommentDtoTransform;

@RestController
@RequestMapping("/book/{bookId}/comment")
@RequiredArgsConstructor
public class BookCommentRestController {
    private final BookCommentReactDao bookCommentReactDao;

    @GetMapping
    @Transactional(readOnly=true)
    public Flux<BookCommentDto> findAll(@PathVariable("bookId") String bookId) {
        return bookCommentReactDao.getByBookId(bookId).map(BookCommentDtoTransform::getBookCommentDto);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly=true)
    public Mono<BookCommentDto> findById(@PathVariable("bookId") String bookId, @PathVariable("id") String id) {
        return bookCommentReactDao.findById(id).map(BookCommentDtoTransform::getBookCommentDto);
    }

    @PostMapping
    @Transactional
    public Mono<BookCommentDto> save(@PathVariable("bookId") String bookId, @RequestBody BookCommentDto bookCommentDto) {
        return bookCommentReactDao.save(BookCommentDtoTransform.getBookComment(bookCommentDto)).map(BookCommentDtoTransform::getBookCommentDto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public Mono<Void> deleteById(@PathVariable("bookId") String bookId, @PathVariable("id") String id) {
        return bookCommentReactDao.deleteById(id);
    }

}
