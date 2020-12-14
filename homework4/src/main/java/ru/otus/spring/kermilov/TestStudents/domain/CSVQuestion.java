package ru.otus.spring.kermilov.TestStudents.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class CSVQuestion {

    public boolean compare(CSVQuestion q) {
        return q.getQuestion().equals(this.question) &&
               q.getAnswers().equals(this.answers);
    }

    @Getter
    private final String question;
    @Getter
    private final Map<String, Boolean> answers;
}
