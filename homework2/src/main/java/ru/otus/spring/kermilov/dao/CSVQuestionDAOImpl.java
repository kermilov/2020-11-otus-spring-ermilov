package ru.otus.spring.kermilov.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.kermilov.Main;
import ru.otus.spring.kermilov.domain.CSVQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Service
public class CSVQuestionDAOImpl implements CSVQuestionDAO { 
    private final String csvPath;

    public CSVQuestionDAOImpl(@Value("${csv.name}") String csvPath) {
        this.csvPath = csvPath;
    }

    @Override
    public ArrayList<CSVQuestion> findAll() {
        ArrayList<CSVQuestion> list = new ArrayList<CSVQuestion>();
        Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream(csvPath));
        while (scanner.hasNext()) {
            // читаем всю строку, это вопрос с вариантами ответов
            scanner.useDelimiter("\r\n");
            // разделяем относительно запятой
            String[] csvQuestionArray = scanner.next().split(",");
            int len = csvQuestionArray.length;
            if (len == 1 || len % 2 == 0) {
                throw new RuntimeException("Incorrect CSVQuestion format");
            }
            HashMap<String, Boolean> answers = new HashMap<String, Boolean>();
            for (int i = 1; i < len; i+=2) {
                answers.put(csvQuestionArray[i], (csvQuestionArray[i+1].equalsIgnoreCase("1")) ? true : false);
            }
            list.add(new CSVQuestion(csvQuestionArray[0], answers));
        }
        return list;
    }
}
