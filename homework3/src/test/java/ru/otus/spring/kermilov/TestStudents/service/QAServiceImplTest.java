package ru.otus.spring.kermilov.TestStudents.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.kermilov.TestStudents.dao.CSVQuestionDAOImpl;
import ru.otus.spring.kermilov.TestStudents.domain.CSVQuestion;
import ru.otus.spring.kermilov.TestStudents.mock.CSVQuestionMock;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QAServiceImplTest {
    @Mock
    private CSVQuestionDAOImpl dao;
    @Mock
    private LocalPrintService lps;

    @BeforeEach
    void beforeEach() {
        given(dao.findAll()).willReturn(new ArrayList<CSVQuestion>() {{
            add(new CSVQuestionMock("1"));
            add(new CSVQuestionMock("2"));
            add(new CSVQuestionMock("3"));
        }});
    }

    void testStudentTest(String input, int expected_result) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertThat(new QAServiceImpl(dao,lps).testStudent()).isEqualTo(expected_result);
        System.setIn(System.in);
    }

    @Test
    void testStudentResult0Test() {
        testStudentTest(new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName() + "\n"
                +"Answer21\n"
                +"Answer11\n"
                +"Answer31\n", 0);
    }

    @Test
    void testStudentResult1Test() {
        testStudentTest(new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName() + "\n"
                +"Answer2\n"
                +"Answer1\n"
                +"Answer3\n", 1);
    }

    @Test
    void testStudentResult3Test() {
        testStudentTest(new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName() + "\n"
                +"Answer1\n"
                +"Answer2\n"
                +"Answer3\n", 3);
    }
}