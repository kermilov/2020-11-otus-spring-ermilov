package ru.otus.spring.kermilov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.kermilov.service.QAServiceImpl;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QAServiceImpl service = context.getBean(QAServiceImpl.class);

        if (!service.readCSVQuestions()) {
            System.out.println("Service readQA fail");
        }
        if (!service.printCSVQuestions()) {
            System.out.println("Service printQuestions fail");
        }
    }
}
