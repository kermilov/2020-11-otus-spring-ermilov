package ru.otus.spring.kermilov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.kermilov.config.AppConfig;
import ru.otus.spring.kermilov.service.QAServiceImpl;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        QAServiceImpl service = context.getBean(QAServiceImpl.class);
        service.testStudent();
    }
}
