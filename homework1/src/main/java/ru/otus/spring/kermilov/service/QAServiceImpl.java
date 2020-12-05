package ru.otus.spring.kermilov.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.kermilov.dao.CSVQuestionDAO;
import ru.otus.spring.kermilov.domain.CSVQuestion;

import java.util.ArrayList;

@RequiredArgsConstructor
public class QAServiceImpl implements QAService {
    private final CSVQuestionDAO dao;

    @Override
    public void printCSVQuestions() {
        ArrayList<CSVQuestion> csvQuestions = dao.findAll();
        if (csvQuestions.size() == 0) {
            throw new RuntimeException("Empty question list");
        }
        csvQuestions.forEach(CSVQuestion -> {
            System.out.println(CSVQuestion.getQuestion());
            CSVQuestion.getAnswers().forEach((answer, right) -> { System.out.println(answer);});
        });
    }
}
