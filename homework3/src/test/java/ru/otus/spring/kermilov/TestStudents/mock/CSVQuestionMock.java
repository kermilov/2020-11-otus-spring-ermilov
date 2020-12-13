package ru.otus.spring.kermilov.TestStudents.mock;

import ru.otus.spring.kermilov.TestStudents.domain.CSVQuestion;

import java.util.HashMap;

public class CSVQuestionMock extends CSVQuestion {
    public CSVQuestionMock(String num) {
        super("Question"+num, new HashMap<String, Boolean>() {{
            put("Answer1", (num.equalsIgnoreCase("1") ? true : false));
            put("Answer2", (num.equalsIgnoreCase("2") ? true : false));
            put("Answer3", (num.equalsIgnoreCase("3") ? true : false));
        }});
    }
}
