package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.kermilov.books.dto.BookDto;
import ru.otus.spring.kermilov.books.service.BookDtoService;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookDtoService bookDtoService;

    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("books", bookDtoService.findAll());
        return "list";
    }

    @GetMapping("/insert")
    public String insertPage(Model model) {
        model.addAttribute("book", bookDtoService.getNew());
        return "edit";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("book", bookDtoService.findById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String saveBook(BookDto bookDto, Model model) {
        bookDtoService.save(bookDto);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        model.addAttribute("book", bookDtoService.findById(id));
        return "delete";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        bookDtoService.deleteById(id);
        return "redirect:/";
    }
}

