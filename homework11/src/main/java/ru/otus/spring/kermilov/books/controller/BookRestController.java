package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.kermilov.books.dto.BookDto;
import ru.otus.spring.kermilov.books.service.BookDtoService;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookRestController {
    private final BookDtoService bookDtoService;

    @GetMapping
    public Flux<BookDto> findAll() {
        return bookDtoService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<BookDto> findById(@PathVariable("id") String id) {
        return bookDtoService.findById(id);
    }

    @PostMapping
    public Mono<BookDto> save(@RequestBody BookDto bookDto) {
        return bookDtoService.save(bookDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return bookDtoService.deleteById(id);
    }
}
