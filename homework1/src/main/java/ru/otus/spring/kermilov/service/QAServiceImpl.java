package ru.otus.spring.kermilov.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.kermilov.Main;
import ru.otus.spring.kermilov.dao.CSVQuestionDAO;
import ru.otus.spring.kermilov.domain.CSVQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
public class QAServiceImpl implements QAService {
    private final CSVQuestionDAO dao;
    private final String csvPath;

    @Override
    public void readCSVQuestions() {
        Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream(csvPath));
        int columnNumber = 1;
        String question = "";
        Map<String, Boolean> answers = new HashMap<String, Boolean>();
        String answer = "";
        Boolean right = false;
        while (scanner.hasNext()) {
            // последняя колонка - читаем до конца строки, иначе до запятой
            scanner.useDelimiter((columnNumber == 7) ? "\r\n" : ",");

            switch (columnNumber) {
                case 1: {
                    question = scanner.next();
                    if (question.isEmpty()) {
                        throw new RuntimeException("Can't scan question");
                    }
                    question = question.replace("\r\n","");
                    break;
                }
                case 2:
                case 4:
                case 6: {
                    answer = scanner.next();
                    if (answer.isEmpty()) {
                        throw new RuntimeException("Can't scan answer");
                    }
                    break;
                }
                case 3:
                case 5:
                case 7: {
                    right = (scanner.next().equalsIgnoreCase("1")) ? true : false;
                    answers.put(answer, right);
                    break;
                }
            }

            if (columnNumber == 7) {
                dao.save(new CSVQuestion(question, answers));
                columnNumber = 1;
                question = "";
                answers.clear();
                answer = "";
                right = false;
            } else {
                columnNumber++;
            }
        }
    }

    @Override
    public void printCSVQuestions() {
        ArrayList<CSVQuestion> csvQuestions = dao.findAll();
        if (csvQuestions.size() == 0) {
            throw new RuntimeException("Empty question list");
        }
        csvQuestions.forEach(CSVQuestion -> {
            System.out.println(CSVQuestion.getQuestion());
        });
    }
}
