package ru.otus.spring.kermilov.TestStudents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.kermilov.TestStudents.dao.CSVQuestionDAO;
import ru.otus.spring.kermilov.TestStudents.domain.CSVQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class QAServiceImpl implements QAService {
    private final CSVQuestionDAO dao;
    private final LocalPrintService lps;

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
        csvQuestions.forEach(csvQuestion -> {
            ask(csvQuestion);
            if (verify(in, csvQuestion).equals("good")) {
                result.getAndIncrement();
            }
        });
        return result.get();
    }

    private String verify(Scanner in, CSVQuestion csvQuestion) {
        lps.localPrint("please.enter.answer");
        CSVQuestion q = new CSVQuestion(csvQuestion.getQuestion(), new HashMap<String, Boolean>(csvQuestion.getAnswers()));
        String a = in.nextLine();
        String result_str = "fail";
        if (q.getAnswers().containsKey(a)) {
            q.getAnswers().replace(a,true);
            result_str = csvQuestion.equals(q) ? "good" : "fail";
        }
        lps.localPrint(result_str);
        return result_str;
    }

    private void ask(CSVQuestion csvQuestion) {
        System.out.println(csvQuestion.getQuestion());
        csvQuestion.getAnswers().forEach((answer, right) -> {
            System.out.println(answer);
        });
    }

    private ArrayList<CSVQuestion> getCsvQuestions() {
        ArrayList<CSVQuestion> csvQuestions = dao.findAll();
        if (csvQuestions.size() == 0) {
            throw new RuntimeException("Empty question list");
        }
        return csvQuestions;
    }
}
