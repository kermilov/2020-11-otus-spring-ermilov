package ru.otus.spring.kermilov.TestStudents.dao;

import ru.otus.spring.kermilov.TestStudents.domain.Question;

import java.util.ArrayList;

public interface QuestionDAO {
    ArrayList<Question> findAll();
}
