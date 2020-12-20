package ru.otus.spring.kermilov.TestStudents.mock;

import ru.otus.spring.kermilov.TestStudents.domain.Question;

import java.util.HashMap;

public class QuestionMock extends Question {
    public QuestionMock(String num, int countAnswers) {
        super("Question "+num+"?", new HashMap<String, Boolean>());
        for(int i =1;i<=countAnswers;i++)
            this.getAnswers().put("Answer "+num+"-"+Integer.toString(i), (num.equalsIgnoreCase(Integer.toString(i)) ? true : false));
    }

    public QuestionMock(String num) {
        super("Question "+num+"?", new HashMap<String, Boolean>());
        this.getAnswers().put("Answer "+num+"-1", (num.equalsIgnoreCase("1") ? true : false));
        this.getAnswers().put("Answer "+num+"-2", (num.equalsIgnoreCase("2") ? true : false));
        this.getAnswers().put("Answer "+num+"-3", (num.equalsIgnoreCase("3") ? true : false));
    }
}
