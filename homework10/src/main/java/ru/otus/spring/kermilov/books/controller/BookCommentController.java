package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookCommentController {

    @GetMapping("/comment")
    public String listPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookId", id);
        return "comment-table";
    }

    @GetMapping("/comment/insert")
    public String insertPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookCommentId", 0);
        model.addAttribute("bookId", id);
        model.addAttribute("action", 1);
        return "comment-form";
    }

    @GetMapping("/comment/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookCommentId", id);
        model.addAttribute("bookId", id);
        model.addAttribute("action", 2);
        return "comment-form";
    }

    @GetMapping("/comment/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookCommentId", id);
        model.addAttribute("bookId", id);
        model.addAttribute("action", 3);
        return "comment-form";
    }
}
