package ru.otus.spring.kermilov.TestStudents.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.kermilov.TestStudents.TestStudentsApplication;
import ru.otus.spring.kermilov.TestStudents.config.AppProps;
import ru.otus.spring.kermilov.TestStudents.config.ResProps;
import ru.otus.spring.kermilov.TestStudents.domain.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class CSVQuestionDAOImpl implements QuestionDAO {
    private final AppProps appProps;
    private final ResProps resProps;

    private Question nextScanToQuestion(Scanner scanner) {
        String[] csvQuestionArray = scanner.next().split(",");
        int len = csvQuestionArray.length;
        if (len == 1 || len % 2 == 0) {
            throw new RuntimeException("Incorrect CSVQuestion format");
        }
        HashMap<String, Boolean> answers = new HashMap<String, Boolean>();
        for (int i = 1; i < len; i+=2) {
            answers.put(csvQuestionArray[i], (csvQuestionArray[i+1].equalsIgnoreCase("1")) ? true : false);
        }
        return new Question(csvQuestionArray[0], answers);
    }

    @Override
    public ArrayList<Question> findAll() {
        ArrayList<Question> list = new ArrayList<Question>();
        Scanner scanner = new Scanner(TestStudentsApplication.class.getClassLoader().getResourceAsStream(resProps.getCsvName().replaceFirst("@locale", appProps.getLocale().toString())));
        scanner.useDelimiter("\r\n");
        while (scanner.hasNext()) {
            list.add(nextScanToQuestion(scanner));
        }
        return list;
    }
}
