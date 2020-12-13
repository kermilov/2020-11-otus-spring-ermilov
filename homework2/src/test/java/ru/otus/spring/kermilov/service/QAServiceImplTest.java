package ru.otus.spring.kermilov.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.kermilov.dao.CSVQuestionDAOImpl;
import ru.otus.spring.kermilov.domain.CSVQuestion;
import ru.otus.spring.kermilov.mock.CSVQuestionMock;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QAServiceImplTest {
    @Mock
    private CSVQuestionDAOImpl dao;

    @Test
    void testStudentResult0Test() {
        given(dao.findAll()).willReturn(new ArrayList<CSVQuestion>() {{
            add(new CSVQuestionMock("1"));
            add(new CSVQuestionMock("2"));
            add(new CSVQuestionMock("3"));
        }});
        String methodName = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        ByteArrayInputStream in = new ByteArrayInputStream((methodName + "\n"
                +"Answer21\n"
                +"Answer11\n"
                +"Answer31\n")
                .getBytes());
        System.setIn(in);
        QAServiceImpl service = new QAServiceImpl(dao);
        int result = service.testStudent();
        assertThat(result).isEqualTo(0);
        System.setIn(System.in);
    }

    @Test
    void testStudentResult1Test() {
        given(dao.findAll()).willReturn(new ArrayList<CSVQuestion>() {{
            add(new CSVQuestionMock("1"));
            add(new CSVQuestionMock("2"));
            add(new CSVQuestionMock("3"));
        }});
        String methodName = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        ByteArrayInputStream in = new ByteArrayInputStream((methodName + "\n"
                +"Answer2\n"
                +"Answer1\n"
                +"Answer3\n")
                .getBytes());
        System.setIn(in);
        QAServiceImpl service = new QAServiceImpl(dao);
        int result = service.testStudent();
        assertThat(result).isEqualTo(1);
        System.setIn(System.in);
    }

    @Test
    void testStudentResult3Test() {
        given(dao.findAll()).willReturn(new ArrayList<CSVQuestion>() {{
            add(new CSVQuestionMock("1"));
            add(new CSVQuestionMock("2"));
            add(new CSVQuestionMock("3"));
        }});
        String methodName = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        ByteArrayInputStream in = new ByteArrayInputStream((methodName + "\n"
                +"Answer1\n"
                +"Answer2\n"
                +"Answer3\n")
                .getBytes());
        System.setIn(in);
        QAServiceImpl service = new QAServiceImpl(dao);
        int result = service.testStudent();
        assertThat(result).isEqualTo(3);
        System.setIn(System.in);
    }
}