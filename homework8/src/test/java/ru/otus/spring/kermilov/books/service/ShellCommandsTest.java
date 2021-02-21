package ru.otus.spring.kermilov.books.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShellCommandsTest {

    @Autowired
    private ShellCommands shellCommands;

    @Test
    void saveGenreShouldUpdateByName() {
        val id = shellCommands.saveGenre("Genre 1").getId();
        assertThat(shellCommands.saveGenre("Genre 1").getId()).isEqualTo(id);
    }

    @Test
    void saveAuthorShouldUpdateByName() {
        val id = shellCommands.saveAuthor("Author 1").getId();
        assertThat(shellCommands.saveAuthor("Author 1").getId()).isEqualTo(id);
    }

    @Test
    void saveBookShouldUpdateByName() {
        val id = shellCommands.saveBook("Book 1", "Author 1", "Genre 1").getId();
        assertThat(shellCommands.saveBook("Book 1", "Author 2", "Genre 1, Genre 2").getId()).isEqualTo(id);
    }

    @Test
    void saveBookCommentShouldUpdateByName() {
        shellCommands.saveBook("Book 1", "Author 1", "Genre 1");
        val saveBookComment = shellCommands.saveBookComment("Book 1", "Book Comment 1");
        System.out.println(saveBookComment);
        val findBookComment = shellCommands.findBookComment("Book 1");
        System.out.println(findBookComment.get(findBookComment.size()-1));
        assertThat(findBookComment.get(findBookComment.size()-1)).isEqualTo(saveBookComment);
    }
}