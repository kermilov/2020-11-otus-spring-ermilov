package ru.otus.spring.kermilov.TestStudents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.kermilov.TestStudents.config.AppProps;
import ru.otus.spring.kermilov.TestStudents.service.QAServiceImpl;

@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class TestStudentsApplication {

	public static void main(String[] args) {

		var context = SpringApplication.run(TestStudentsApplication.class, args);
		var service = context.getBean(QAServiceImpl.class);
		service.testStudent();
	}

}
