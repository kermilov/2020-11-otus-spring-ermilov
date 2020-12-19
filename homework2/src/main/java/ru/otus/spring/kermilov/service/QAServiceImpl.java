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
        ArrayList<CSVQuestion> csvQuestions = getCsvQuestions();
        csvQuestions.forEach(CSVQuestion -> {
            ask(CSVQuestion);
        });
    }

    @Override
    public int testStudent() {
        AtomicInteger result = new AtomicInteger();
        ArrayList<CSVQuestion> csvQuestions = getCsvQuestions();
        Scanner in = new Scanner(System.in);
        sayHello(in);
        csvQuestions.forEach(csvQuestion -> {
            ask(csvQuestion);
            if (verify(in, csvQuestion).equals("Good!")) {
                result.getAndIncrement();
            }
        });
        return result.get();
    }

    private String verify(Scanner in, CSVQuestion csvQuestion) {
        System.out.println("Please, enter answer:");
        CSVQuestion q = new CSVQuestion(csvQuestion.getQuestion(), new HashMap<String, Boolean>(csvQuestion.getAnswers()));
        String a = in.nextLine();
        String result_str = "Fail!";
        if (q.getAnswers().containsKey(a)) {
            q.getAnswers().replace(a,true);
            result_str = csvQuestion.equals(q) ? "Good!" : "Fail!";
        }
        System.out.println(result_str);
        return result_str;
    }

    private void ask(CSVQuestion csvQuestion) {
        System.out.println(csvQuestion.getQuestion());
        csvQuestion.getAnswers().forEach((answer, right) -> {
            System.out.println(answer);
        });
    }

    private void sayHello(Scanner in) {
        System.out.println("Please, introduce yourself:");
        String name = in.nextLine();
        System.out.println("Hello, " + name + "!");
    }

    private ArrayList<CSVQuestion> getCsvQuestions() {
        ArrayList<CSVQuestion> csvQuestions = dao.findAll();
        if (csvQuestions.size() == 0) {
            throw new RuntimeException("Empty question list");
        }
        return csvQuestions;
    }
}
