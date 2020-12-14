package ru.otus.spring.kermilov.TestStudents.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.kermilov.TestStudents.TestStudentsApplication;
import ru.otus.spring.kermilov.TestStudents.config.AppProps;
import ru.otus.spring.kermilov.TestStudents.domain.CSVQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class CSVQuestionDAOImpl implements CSVQuestionDAO {
    private final AppProps props;

    @Override
    public ArrayList<CSVQuestion> findAll() {
        ArrayList<CSVQuestion> list = new ArrayList<CSVQuestion>();
        Scanner scanner = new Scanner(TestStudentsApplication.class.getClassLoader().getResourceAsStream(props.getCsvName().replaceFirst("@locale",props.getLocale().toString())));
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
