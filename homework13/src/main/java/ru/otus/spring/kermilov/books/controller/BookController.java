package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.kermilov.books.dao.AuthorDao;
import ru.otus.spring.kermilov.books.dao.GenreDao;
import ru.otus.spring.kermilov.books.dto.BookDto;
import ru.otus.spring.kermilov.books.service.BookDtoService;

@Controller
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class BookController {
    private final BookDtoService bookDtoService;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("books", bookDtoService.findAll());
        return "list";
    }

    @GetMapping("/book/insert")
    public String insertPage(Model model) {
        model.addAttribute("book", bookDtoService.getNew());
        model.addAttribute("authorDao", authorDao);
        model.addAttribute("genreDao", genreDao);
        return "edit";
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("book", bookDtoService.findById(id));
        model.addAttribute("authorDao", authorDao);
        model.addAttribute("genreDao", genreDao);
        return "edit";
    }

    @PostMapping("/book/edit")
    public String saveBook(BookDto bookDto, Model model) {
        bookDtoService.save(bookDto);
        return "redirect:/";
    }

    @GetMapping("/book/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        model.addAttribute("book", bookDtoService.findById(id));
        return "delete";
    }

    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        bookDtoService.deleteById(id);
        return "redirect:/";
    }
}

