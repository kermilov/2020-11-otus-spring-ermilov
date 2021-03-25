package ru.otus.spring.kermilov.books.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import ru.otus.spring.kermilov.books.dao.BookReactDao;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.Genre;
import ru.otus.spring.kermilov.books.dto.BookDto;
import ru.otus.spring.kermilov.books.transform.BookDtoTransform;

@WebFluxTest(controllers = BookRestController.class)
class BookRestControllerTest {
    private static final Book book = new Book("604fb56771dbfa5124889cf9",
                                              "Test Book 1",
                                              new Author("Test Author 1"),
                                              List.of(new Genre("Test Genre 1"), new Genre("Test Genre 2")));
    private static final BookDto bookDto = BookDtoTransform.getBookDto(book);

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private BookReactDao bookReactDao;
    @MockBean
    private BookDtoTransform bookDtoTransform;

    @Test
    void listBooks() {
        Mockito.when(bookReactDao.findAll()).thenReturn(Flux.just(book));
        webTestClient.get()
                .uri("/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .value(value -> {
                    assertThat(value.size() == 2);
                    assertEquals(bookDto, value.get(0));
                });

    }
}
