package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.kermilov.books.dto.BookCommentDto;
import ru.otus.spring.kermilov.books.service.BookCommentDtoService;

@RestController
@RequestMapping("/book/{bookId}/comment")
@RequiredArgsConstructor
public class BookCommentRestController {
    private final BookCommentDtoService bookCommentDtoService;

    @GetMapping
    public Flux<BookCommentDto> findAll(@PathVariable("bookId") String bookId) {
        return bookCommentDtoService.getByBookId(bookId);
    }

    @GetMapping("/{id}")
    public Mono<BookCommentDto> findById(@PathVariable("bookId") String bookId, @PathVariable("id") String id) {
        return bookCommentDtoService.getBookCommentById(id);
    }

    @PostMapping
    public Mono<BookCommentDto> save(@PathVariable("bookId") String bookId, @RequestBody BookCommentDto bookCommentDto) {
        return bookCommentDtoService.save(bookCommentDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("bookId") String bookId, @PathVariable("id") String id) {
        return bookCommentDtoService.deleteById(id);
    }

}
