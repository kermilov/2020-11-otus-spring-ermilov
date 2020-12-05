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
        while (scanner.hasNext()) {
            // читаем всю строку, это вопрос с вариантами ответов
            scanner.useDelimiter("\r\n");
            // разделяем относительно запятой
            String[] csvQuestionArray = scanner.next().split(",");
            if (csvQuestionArray.length != 7) {
                throw new RuntimeException("Incorrect CSVQuestion format");
            }
            dao.save(new CSVQuestion(csvQuestionArray[0], new HashMap<String, Boolean>() {{
                put(csvQuestionArray[1], (csvQuestionArray[2].equalsIgnoreCase("1")) ? true : false);
                put(csvQuestionArray[3], (csvQuestionArray[4].equalsIgnoreCase("1")) ? true : false);
                put(csvQuestionArray[5], (csvQuestionArray[6].equalsIgnoreCase("1")) ? true : false);
            }}));
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
            CSVQuestion.getAnswers().forEach((answer, right) -> { System.out.println(answer);});
        });
    }
}
