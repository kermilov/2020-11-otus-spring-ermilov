package ru.otus.spring.kermilov.TestStudents.service;

public interface PrintService {
    void print(String s);
    void print(String s, Object[] objects);
    String prepareToPrint(String s);
    String prepareToPrint(String s, Object[] objects);
}
