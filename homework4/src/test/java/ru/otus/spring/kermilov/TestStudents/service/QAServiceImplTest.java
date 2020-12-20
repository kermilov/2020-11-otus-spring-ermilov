package ru.otus.spring.kermilov.TestStudents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.kermilov.TestStudents.dao.CSVQuestionDAOImpl;
import ru.otus.spring.kermilov.TestStudents.domain.Question;
import ru.otus.spring.kermilov.TestStudents.mock.QuestionMock;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class QAServiceImplTest {
    @MockBean
    private CSVQuestionDAOImpl dao;
    @MockBean
    private PrintService lps;

    @BeforeEach
    void beforeEach() {
        given(dao.findAll()).willReturn(new ArrayList<Question>() {{
            add(new QuestionMock("1"));
            add(new QuestionMock("2"));
            add(new QuestionMock("3"));
        }});
    }

    void testStudentTest(String input, int expected_result) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertThat(new QAServiceImpl(dao,lps).testStudent()).isEqualTo(expected_result);
        System.setIn(System.in);
    }

    @Test
    void testStudentResult0Test() {
        testStudentTest(
                "Answer21\n"
                +"Answer11\n"
                +"Answer31\n", 0);
    }

    @Test
    void testStudentResult1Test() {
        testStudentTest("Answer 1-2\n"
                +"Answer 2-1\n"
                +"Answer 3-3\n", 1);
    }

    @Test
    void testStudentResult3Test() {
        testStudentTest("Answer 1-1\n"
                +"Answer 2-2\n"
                +"Answer 3-3\n", 3);
    }
}