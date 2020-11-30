package ru.otus.spring.kermilov.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class CSVQuestion {
    @Getter
    private final String question;
    private final Map<String, Boolean> answers;
}
