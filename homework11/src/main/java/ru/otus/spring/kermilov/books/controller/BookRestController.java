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

    // @Bean
    // public RouterFunction<ServerResponse> composedRoutes(BookReactDao bookReactDao, BookDtoTransform bookDtoTransform) {
    //     return route()
    //             .GET("/book",
    //                     request -> ok()
    //                             .body(bookReactDao.findAll().map(BookDtoTransform::getBookDto), BookDto.class))
    //             .GET("/book/{id}", accept(APPLICATION_JSON),
    //                     request -> bookReactDao.findById(request.pathVariable("id")).map(BookDtoTransform::getBookDto)
    //                             .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromObject(book))))
    //             .GET("/book/{id}", accept(APPLICATION_JSON),
    //                     request -> ok().body(
    //                             bookReactDao.findById(request.pathVariable("id")).map(BookDtoTransform::getBookDto),
    //                             BookDto.class))
    //             .POST("/book", accept(APPLICATION_JSON),
    //                     request -> request.body(toMono(BookDto.class))
    //                             .doOnNext(bookDto -> bookReactDao.save(bookDtoTransform.getBook(bookDto)))
    //                             .then(ok().build()))
    //             .DELETE("/book/{id}",
    //                     request -> ok().body(
    //                             bookReactDao.deleteById(request.pathVariable("id")),
    //                             BookDto.class))
    //             .build();
    // }

}
