package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.kermilov.books.dao.BookReactDao;
import ru.otus.spring.kermilov.books.dto.BookDto;
import ru.otus.spring.kermilov.books.transform.BookDtoTransform;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookRestController {
    private final BookReactDao bookReactDao;
    private final BookDtoTransform bookDtoTransform;

    @GetMapping
    @Transactional(readOnly=true)
    public Flux<BookDto> findAll() {
        return bookReactDao.findAll().map(BookDtoTransform::getBookDto);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly=true)
    public Mono<BookDto> findById(@PathVariable("id") String id) {
        return bookReactDao.findById(id).map(BookDtoTransform::getBookDto);
    }

    @PostMapping
    @Transactional
    public Mono<BookDto> save(@RequestBody BookDto bookDto) {
        return bookReactDao.save(bookDtoTransform.getBook(bookDto)).map(BookDtoTransform::getBookDto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return bookReactDao.deleteById(id);
    }

}
