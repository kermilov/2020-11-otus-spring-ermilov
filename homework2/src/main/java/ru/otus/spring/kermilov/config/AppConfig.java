package ru.otus.spring.kermilov.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.kermilov.dao.CSVQuestionDAO;
import ru.otus.spring.kermilov.dao.CSVQuestionDAOImpl;
import ru.otus.spring.kermilov.service.QAServiceImpl;

@Configuration
public class AppConfig {
    @Bean
    CSVQuestionDAO QADao(@Value("questions.csv") String csvPath) {
        return new CSVQuestionDAOImpl(csvPath);
    }

    @Bean
    QAServiceImpl QAService(CSVQuestionDAO dao) {
        return new QAServiceImpl(dao);
    }

}
