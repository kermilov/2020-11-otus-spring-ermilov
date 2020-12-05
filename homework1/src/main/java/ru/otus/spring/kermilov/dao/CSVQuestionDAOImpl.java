package ru.otus.spring.kermilov.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.kermilov.Main;
import ru.otus.spring.kermilov.domain.CSVQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@RequiredArgsConstructor
public class CSVQuestionDAOImpl implements CSVQuestionDAO {
    private final String csvPath;

    @Override
    public ArrayList<CSVQuestion> findAll() {
        ArrayList<CSVQuestion> list = new ArrayList<CSVQuestion>();
        Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream(csvPath));
        while (scanner.hasNext()) {
            // читаем всю строку, это вопрос с вариантами ответов
            scanner.useDelimiter("\r\n");
            // разделяем относительно запятой
            String[] csvQuestionArray = scanner.next().split(",");
            if (csvQuestionArray.length != 7) {
                throw new RuntimeException("Incorrect CSVQuestion format");
            }
            list.add(new CSVQuestion(csvQuestionArray[0], new HashMap<String, Boolean>() {{
                put(csvQuestionArray[1], (csvQuestionArray[2].equalsIgnoreCase("1")) ? true : false);
                put(csvQuestionArray[3], (csvQuestionArray[4].equalsIgnoreCase("1")) ? true : false);
                put(csvQuestionArray[5], (csvQuestionArray[6].equalsIgnoreCase("1")) ? true : false);
            }}));
        }
        return list;
    }
}
