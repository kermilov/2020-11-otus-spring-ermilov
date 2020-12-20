package ru.otus.spring.kermilov.TestStudents.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.kermilov.TestStudents.config.AppProps;
import ru.otus.spring.kermilov.TestStudents.config.ResProps;
import ru.otus.spring.kermilov.TestStudents.domain.Question;
import ru.otus.spring.kermilov.TestStudents.mock.QuestionMock;

import java.util.ArrayList;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс DAO")
class CSVQuestionDAOImplTest {
    private final AppProps appProps = new AppProps();
    private final ResProps resProps = new ResProps();

    @BeforeEach
    void beforeEach () {
        appProps.setLocale(new Locale(""));
    }
    @DisplayName("корректно реагирует на пустой файл")
    @Test
    void findAllEmptyTest() {
        resProps.setCsvName("emptyTest.csv");
        CSVQuestionDAOImpl dao = new CSVQuestionDAOImpl(appProps, resProps);
        ArrayList<Question> list = dao.findAll();
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("корректно реагирует на неверный формат файла с вопросами")
    @Test
    void findAllIncorrectTest() {
        resProps.setCsvName("incorrectTest.csv");
        CSVQuestionDAOImpl dao = new CSVQuestionDAOImpl(appProps, resProps);
        assertThrows(RuntimeException.class, () -> {dao.findAll();});
    }

    @DisplayName("корректно реагирует на верный формат файла с вопросами")
    @Test
    void findAllCorrectTest() {
        resProps.setCsvName("correctTest.csv");
        ArrayList<Question> expectedList = new ArrayList<Question>() {{
            add((Question) new QuestionMock("1",3));
            add((Question) new QuestionMock("2",5));
        }};
        ArrayList<Question> findList = new CSVQuestionDAOImpl(appProps, resProps).findAll();
        assertThat(expectedList).isEqualTo(findList);
    }
}