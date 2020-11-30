package ru.otus.spring.kermilov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.kermilov.service.QAService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QAService service = context.getBean(QAService.class);

        if (!service.readCSVQuestions()) {
            System.out.println("Service readQA fail");
        }
        if (!service.printCSVQuestions()) {
            System.out.println("Service printQuestions fail");
        }
    }
}
