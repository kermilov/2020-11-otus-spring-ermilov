package ru.otus.spring.kermilov.TestStudents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.kermilov.TestStudents.config.AppProps;
import ru.otus.spring.kermilov.TestStudents.config.ResProps;
import ru.otus.spring.kermilov.TestStudents.service.QAServiceImpl;

@SpringBootApplication
@EnableConfigurationProperties({AppProps.class, ResProps.class})
public class TestStudentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestStudentsApplication.class, args);
	}

}
