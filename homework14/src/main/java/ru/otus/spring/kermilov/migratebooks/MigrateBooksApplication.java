package ru.otus.spring.kermilov.migratebooks;

import com.github.cloudyrock.spring.v5.EnableMongock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class MigrateBooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MigrateBooksApplication.class, args);
	}

}
