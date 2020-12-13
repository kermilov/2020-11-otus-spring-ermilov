package ru.otus.spring.kermilov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.kermilov.dao.CSVQuestionDAO;
import ru.otus.spring.kermilov.domain.CSVQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
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
    public int testStudent() {
        ArrayList<CSVQuestion> csvQuestions = dao.findAll();
        AtomicInteger result = new AtomicInteger();
        if (csvQuestions.size() == 0) {
            throw new RuntimeException("Empty question list");
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Please, introduce yourself:");
        String name = in.nextLine();
        System.out.println("Hello, " + name + "!");
        csvQuestions.forEach(csvQuestion -> {
            System.out.println(csvQuestion.getQuestion());
            csvQuestion.getAnswers().forEach((answer, right) -> { System.out.println(answer);});
            System.out.println("Please, enter answer:");
            CSVQuestion q = new CSVQuestion(csvQuestion.getQuestion(), new HashMap<String, Boolean>(csvQuestion.getAnswers()));
            String a = in.nextLine();
            String result_str = "Fail!";
            if (q.getAnswers().containsKey(a)) {
                q.getAnswers().replace(a,true);
                result_str = csvQuestion.compare(q) ? "Good!" : "Fail!";
            }
            if (result_str.equals("Good!")) {
                result.getAndIncrement();
            }
            System.out.println(result_str);
        });
        return result.get();
    }
}
