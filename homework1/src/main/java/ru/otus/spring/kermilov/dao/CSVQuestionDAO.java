package ru.otus.spring.kermilov.dao;

import ru.otus.spring.kermilov.domain.CSVQuestion;

import java.util.ArrayList;

public interface CSVQuestionDAO {
    boolean save(CSVQuestion CSVQuestion);

    ArrayList<CSVQuestion> findAll();
}
