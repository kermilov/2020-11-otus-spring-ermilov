package ru.otus.spring.kermilov.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.kermilov.dao.CSVQuestionDAO;
import ru.otus.spring.kermilov.domain.CSVQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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

    @Override
    public void testStudent() {
        ArrayList<CSVQuestion> csvQuestions = dao.findAll();
        if (csvQuestions.size() == 0) {
            throw new RuntimeException("Empty question list");
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Please, introduce yourself:");
        String name = in.nextLine();
        System.out.println("Hello, " + name + "!");
        csvQuestions.forEach(CSVQuestion -> {
            System.out.println(CSVQuestion.getQuestion());
            CSVQuestion.getAnswers().forEach((answer, right) -> { System.out.println(answer);});
            System.out.println("Please, enter answer:");
            CSVQuestion q = new CSVQuestion(CSVQuestion.getQuestion(), new HashMap<String, Boolean>(CSVQuestion.getAnswers()));
            String a = in.nextLine();
            if (q.getAnswers().containsKey(a)) {
                q.getAnswers().replace(a,true);
                System.out.println(CSVQuestion.compare(q) ? "Good!" : "Fail!");
            }
            else {
                System.out.println("Fail!");
            }
        });
    }
}
