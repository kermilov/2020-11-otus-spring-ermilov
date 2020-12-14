package ru.otus.spring.kermilov.TestStudents.dao;

import ru.otus.spring.kermilov.TestStudents.domain.CSVQuestion;

import java.util.ArrayList;

public interface CSVQuestionDAO {
    ArrayList<CSVQuestion> findAll();
}
