package ru.otus.spring.kermilov.dao;

import ru.otus.spring.kermilov.domain.CSVQuestion;

import java.util.ArrayList;

public class CSVQuestionDAOImpl implements CSVQuestionDAO {
    private final ArrayList<CSVQuestion> list;

    public CSVQuestionDAOImpl() {
        list = new ArrayList<CSVQuestion>();
    }

    @Override
    public void save(CSVQuestion CSVQuestion) {
        if (!list.add(CSVQuestion)){
            throw new RuntimeException("Can't save CSVQuestion");
        }
    }

    @Override
    public ArrayList<CSVQuestion> findAll() { return list; }
}
