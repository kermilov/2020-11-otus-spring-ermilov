package ru.otus.spring.kermilov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.kermilov.service.QAServiceImpl;

@ComponentScan
@PropertySource("classpath:application.properties")
@Configuration
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QAServiceImpl service = context.getBean(QAServiceImpl.class);
        service.testStudent();
    }
}
