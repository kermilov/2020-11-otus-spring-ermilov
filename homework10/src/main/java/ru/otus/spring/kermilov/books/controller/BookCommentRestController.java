package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.kermilov.books.dto.BookCommentDto;
import ru.otus.spring.kermilov.books.service.BookCommentDtoService;

import java.util.List;

@RestController
@RequestMapping("/book/{bookId}/comment")
@RequiredArgsConstructor
public class BookCommentRestController {
    private final BookCommentDtoService bookCommentDtoService;

    @GetMapping
    public List<BookCommentDto> findAll(@PathVariable("bookId") long bookId) {
        return bookCommentDtoService.getByBookId(bookId);
    }

    @GetMapping("/{id}")
    public BookCommentDto findById(@PathVariable("bookId") long bookId, @PathVariable("id") long id) {
        return bookCommentDtoService.getBookCommentById(id);
    }

    @PostMapping
    public void save(@PathVariable("bookId") long bookId, @RequestBody BookCommentDto bookCommentDto) {
        bookCommentDtoService.save(bookCommentDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("bookId") long bookId, @PathVariable("id") long id) {
        bookCommentDtoService.deleteById(id);
    }

}
