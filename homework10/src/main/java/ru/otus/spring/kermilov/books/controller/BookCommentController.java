package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookCommentController {

    private static final String VIEW_COMMENT_T = "comment-table";
    private static final String VIEW_COMMENT_F = "comment-form";
    private static final String ATTR_BOOK_COMMENT_ID = "bookCommentId";
    private static final String ATTR_BOOK_ID = "bookId";
    private static final String ATTR_ACTION = "action";

    @GetMapping("/comment")
    public String listPage(@RequestParam("id") long id, Model model) {
        model.addAttribute(ATTR_BOOK_ID, id);
        return VIEW_COMMENT_T;
    }

    @GetMapping("/comment/insert")
    public String insertPage(@RequestParam("id") long id, Model model) {
        model.addAttribute(ATTR_BOOK_COMMENT_ID, 0);
        model.addAttribute(ATTR_BOOK_ID, id);
        model.addAttribute(ATTR_ACTION, 1);
        return VIEW_COMMENT_F;
    }

    @GetMapping("/comment/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute(ATTR_BOOK_COMMENT_ID, id);
        model.addAttribute(ATTR_BOOK_ID, id);
        model.addAttribute(ATTR_ACTION, 2);
        return VIEW_COMMENT_F;
    }

    @GetMapping("/comment/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        model.addAttribute(ATTR_BOOK_COMMENT_ID, id);
        model.addAttribute(ATTR_BOOK_ID, id);
        model.addAttribute(ATTR_ACTION, 3);
        return VIEW_COMMENT_F;
    }
}
