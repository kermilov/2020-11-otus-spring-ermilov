package ru.otus.spring.kermilov.dao;

import ru.otus.spring.kermilov.domain.CSVQuestion;

import java.util.ArrayList;

public class CSVQuestionDAOImpl implements CSVQuestionDAO {
    private final ArrayList<CSVQuestion> list;

    public CSVQuestionDAOImpl() {
        list = new ArrayList<CSVQuestion>();
    }

    @Override
    public boolean save(CSVQuestion CSVQuestion) {
        return list.add(CSVQuestion);
    }

    @Override
    public ArrayList<CSVQuestion> findAll() { return list; }
}
