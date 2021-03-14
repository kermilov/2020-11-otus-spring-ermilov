package ru.otus.spring.kermilov.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kermilov.books.dao.BookCommentDao;
import ru.otus.spring.kermilov.books.dao.BookDao;
import ru.otus.spring.kermilov.books.domain.BookComment;
import ru.otus.spring.kermilov.books.dto.BookCommentDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCommentDtoService {
    private final BookDao bookDao;
    private final BookCommentDao bookCommentDao;

    @Transactional(readOnly=true)
    public List<BookCommentDto> getByBookId(long id) {
        return bookCommentDao.getByBookId(id).stream().map(this::getBookCommentDto).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public BookCommentDto getBookCommentById(long id) {
        return getBookCommentDto(bookCommentDao.findById(id).get());
    }

    @Transactional
    public void save(BookCommentDto bookCommentDto) {
        bookCommentDao.save(getBookComment(bookCommentDto));
    }

    @Transactional
    public void deleteById(long id) {
        bookCommentDao.deleteById(id);
    }

    private BookComment getBookComment(BookCommentDto bookCommentDto) {
        BookComment bookComment = new BookComment();
        bookComment.setId(bookCommentDto.getId());
        bookComment.setBook(bookDao.findById(bookCommentDto.getBookId()).get());
        bookComment.setComment(bookCommentDto.getComment());
        return bookComment;
    }

    private BookCommentDto getBookCommentDto(BookComment bookComment) {
        BookCommentDto bookCommentDto = new BookCommentDto();
        bookCommentDto.setId(bookComment.getId());
        bookCommentDto.setBookId(bookComment.getBook().getId());
        bookCommentDto.setBook(bookComment.getBook().getName());
        bookCommentDto.setComment(bookComment.getComment());
        return bookCommentDto;
    }
}
