package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookController {

    @GetMapping("/")
    public String listPage() {
        return "book-table";
    }

    @GetMapping("/book/insert")
    public String insertPage(Model model) {
        model.addAttribute("bookId", 0);
        model.addAttribute("action", 1);
        return "book-form";
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookId", id);
        model.addAttribute("action", 2);
        return "book-form";
    }

    @GetMapping("/book/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookId", id);
        model.addAttribute("action", 3);
        return "book-form";
    }
}

