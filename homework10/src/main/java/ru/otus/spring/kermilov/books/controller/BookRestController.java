package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.kermilov.books.dto.BookDto;
import ru.otus.spring.kermilov.books.service.BookDtoService;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookRestController {
    private final BookDtoService bookDtoService;

    @GetMapping
    public List<BookDto> findAll() {
        return bookDtoService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable("id") long id) {
        return bookDtoService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody BookDto bookDto) {
        bookDtoService.save(bookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        bookDtoService.deleteById(id);
    }
}
