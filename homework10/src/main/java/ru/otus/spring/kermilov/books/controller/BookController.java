package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookController {

    private static final String VIEW_BOOK_T = "book-table";
    private static final String VIEW_BOOK_F = "book-form";
    private static final String ATTR_BOOK_ID = "bookId";
    private static final String ATTR_ACTION = "action";

    @GetMapping("/")
    public String listPage() {
        return VIEW_BOOK_T;
    }

    @GetMapping("/book/insert")
    public String insertPage(Model model) {
        model.addAttribute(ATTR_BOOK_ID, 0);
        model.addAttribute(ATTR_ACTION, 1);
        return VIEW_BOOK_F;
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute(ATTR_BOOK_ID, id);
        model.addAttribute(ATTR_ACTION, 2);
        return VIEW_BOOK_F;
    }

    @GetMapping("/book/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        model.addAttribute(ATTR_BOOK_ID, id);
        model.addAttribute(ATTR_ACTION, 3);
        return VIEW_BOOK_F;
    }
}

