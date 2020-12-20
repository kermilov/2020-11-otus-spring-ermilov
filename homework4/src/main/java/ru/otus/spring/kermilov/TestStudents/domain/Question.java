package ru.otus.spring.kermilov.TestStudents.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class Question {

    @Getter
    private final String question;
    @Getter
    private final Map<String, Boolean> answers;

    @Override
    public boolean equals(Object obj) {
        Question q = (Question) obj;
        return q.getQuestion().equals(this.question) &&
               q.getAnswers().equals(this.answers);
    }
}
