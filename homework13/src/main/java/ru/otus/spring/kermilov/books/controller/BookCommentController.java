package ru.otus.spring.kermilov.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.kermilov.books.dto.BookCommentDto;
import ru.otus.spring.kermilov.books.service.BookCommentDtoService;

@Controller
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class BookCommentController {
    private final BookCommentDtoService bookCommentDtoService;

    @GetMapping("/comment")
    public String listPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookId", id);
        model.addAttribute("bookName", bookCommentDtoService.getBookNameByBookId(id));
        model.addAttribute("bookcomments", bookCommentDtoService.getByBookId(id));
        return "listcomments";
    }

    @PostMapping("/comment/insert")
    public String insertPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookcomment", bookCommentDtoService.getNewByBookId(id));
        return "editcomment";
    }

    @GetMapping("/comment/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookcomment", bookCommentDtoService.getBookCommentById(id));
        return "editcomment";
    }

    @PostMapping("/comment/edit")
    public String saveComment(BookCommentDto bookCommentDto, Model model) {
        long bookId = bookCommentDto.getBookId();
        bookCommentDtoService.save(bookCommentDto);
        return "redirect:/comment?id="+bookId;
    }

    @GetMapping("/comment/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookcomment", bookCommentDtoService.getBookCommentById(id));
        return "deletecomment";
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam("id") long id, Model model) {
        long bookId = bookCommentDtoService.getBookCommentById(id).getBookId();
        bookCommentDtoService.deleteById(id);
        return "redirect:/comment?id="+bookId;
    }
}
