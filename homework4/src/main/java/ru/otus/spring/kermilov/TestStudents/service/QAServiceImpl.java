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
        ArrayList<CSVQuestion> csvQuestions = dao.findAll();
        if (csvQuestions.size() == 0) {
            throw new RuntimeException("Empty question list");
        }
        csvQuestions.forEach(CSVQuestion -> {
            lps.localPrint(CSVQuestion.getQuestion());
            CSVQuestion.getAnswers().forEach((answer, right) -> { lps.localPrint(answer);});
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
        csvQuestions.forEach(csvQuestion -> {
            System.out.println(csvQuestion.getQuestion());
            csvQuestion.getAnswers().forEach((answer, right) -> { System.out.println(answer);});
            lps.localPrint("please.enter.answer");
            CSVQuestion q = new CSVQuestion(csvQuestion.getQuestion(), new HashMap<String, Boolean>(csvQuestion.getAnswers()));
            String a = in.nextLine();
            String result_str = "fail";
            if (q.getAnswers().containsKey(a)) {
                q.getAnswers().replace(a,true);
                result_str = csvQuestion.compare(q) ? "good" : "fail";
            }
            if (result_str.equals("good")) {
                result.getAndIncrement();
            }
            lps.localPrint(result_str);
        });
        return result.get();
    }
}
