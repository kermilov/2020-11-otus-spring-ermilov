package ru.otus.spring.kermilov.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class CSVQuestion {
    @Getter
    private final String question;
    @Getter
    private final Map<String, Boolean> answers;

    @Override
    public boolean equals(Object obj) {
        CSVQuestion q = (CSVQuestion) obj;
        return q.getQuestion().equals(this.question) &&
               q.getAnswers().equals(this.answers);
    }
}
