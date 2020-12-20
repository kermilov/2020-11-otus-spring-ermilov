package ru.otus.spring.kermilov.TestStudents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.kermilov.TestStudents.dao.QuestionDAO;
import ru.otus.spring.kermilov.TestStudents.domain.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class QAServiceImpl implements QAService {
    private final QuestionDAO dao;
    private final PrintService lps;

    @Override
    public void printCSVQuestions() {
        ArrayList<Question> questions = getCsvQuestions();
        questions.forEach(CSVQuestion -> {
            lps.print(CSVQuestion.getQuestion());
            CSVQuestion.getAnswers().forEach((answer, right) -> { lps.print(answer);});
        });
    }

    @Override
    public int testStudent() {
        AtomicInteger result = new AtomicInteger();
        ArrayList<Question> questions = getCsvQuestions();
        Scanner in = new Scanner(System.in);
        sayHello(in);
        questions.forEach(csvQuestion -> {
            ask(csvQuestion);
            if (verify(in, csvQuestion).equals("good")) {
                result.getAndIncrement();
            }
        });
        return result.get();
    }

    private String verify(Scanner in, Question question) {
        lps.print("please.enter.answer");
        Question q = new Question(question.getQuestion(), new HashMap<String, Boolean>(question.getAnswers()));
        String a = in.nextLine();
        String result_str = "fail";
        if (q.getAnswers().containsKey(a)) {
            q.getAnswers().replace(a,true);
            result_str = question.equals(q) ? "good" : "fail";
        }
        lps.print(result_str);
        return result_str;
    }

    private void ask(Question question) {
        System.out.println(question.getQuestion());
        question.getAnswers().forEach((answer, right) -> { System.out.println(answer);});
    }

    private void sayHello(Scanner in) {
        lps.print("please.introduce.yourself");
        String name = in.nextLine();
        lps.print("hello.user",new String[]{name});
    }

    private ArrayList<Question> getCsvQuestions() {
        ArrayList<Question> questions = dao.findAll();
        if (questions.size() == 0) {
            throw new RuntimeException("Empty question list");
        }
        return questions;
    }
}
