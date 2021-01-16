package ru.otus.spring.kermilov.books;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.kermilov.books.dao.AuthorDaoJdbc;
import ru.otus.spring.kermilov.books.domain.Author;

import java.sql.SQLException;

@SpringBootApplication
public class BooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

}
