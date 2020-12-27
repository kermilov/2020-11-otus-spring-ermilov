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
		ApplicationContext context = SpringApplication.run(BooksApplication.class, args);
		AuthorDaoJdbc dao = context.getBean(AuthorDaoJdbc.class);
		dao.save(new Author(1L, "Author 1"));
		//System.out.println(dao.getByID(1L).get().getName());
		try {
			Console.main(args);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

}
