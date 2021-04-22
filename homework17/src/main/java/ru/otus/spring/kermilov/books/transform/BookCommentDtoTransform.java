package ru.otus.spring.kermilov.books.transform;

import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.BookComment;
import ru.otus.spring.kermilov.books.dto.BookCommentDto;

public class BookCommentDtoTransform {

    public static BookComment getBookComment(BookCommentDto bookCommentDto) {
        BookComment bookComment = new BookComment();
        bookComment.setId(bookCommentDto.getId());
        bookComment.setBook(new Book(bookCommentDto.getBookId()));
        bookComment.setComment(bookCommentDto.getComment());
        return bookComment;
    }

    public static BookCommentDto getBookCommentDto(BookComment bookComment) {
        BookCommentDto bookCommentDto = new BookCommentDto();
        bookCommentDto.setId(bookComment.getId());
        bookCommentDto.setBookId(bookComment.getBook().getId());
        bookCommentDto.setBook(bookComment.getBook().getName());
        bookCommentDto.setComment(bookComment.getComment());
        return bookCommentDto;
    }
}
