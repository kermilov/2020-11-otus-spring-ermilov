package ru.otus.spring.kermilov.TestStudents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.kermilov.TestStudents.service.QAServiceImpl;

@SpringBootApplication
public class TestStudentsApplication {

	public static void main(String[] args) {

		var context = SpringApplication.run(TestStudentsApplication.class, args);
		var service = context.getBean(QAServiceImpl.class);
		service.testStudent();
	}

}
