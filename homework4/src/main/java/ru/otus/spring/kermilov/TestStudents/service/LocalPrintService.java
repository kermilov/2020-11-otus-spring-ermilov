package ru.otus.spring.kermilov.TestStudents.service;

public interface LocalPrintService {
    void localPrint(String s);
    void localPrint(String s, Object[] objects);
    String localString(String s);
    String localString(String s, Object[] objects);
}
