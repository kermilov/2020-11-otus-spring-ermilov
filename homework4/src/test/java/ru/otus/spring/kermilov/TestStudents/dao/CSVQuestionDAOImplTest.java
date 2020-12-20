package ru.otus.spring.kermilov.TestStudents.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.kermilov.TestStudents.config.AppProps;
import ru.otus.spring.kermilov.TestStudents.domain.CSVQuestion;
import ru.otus.spring.kermilov.TestStudents.mock.CSVQuestionMock;

import java.util.ArrayList;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс DAO")
class CSVQuestionDAOImplTest {
    private final AppProps props = new AppProps();

    @BeforeEach
    void beforeEach () {
        props.setLocale(new Locale(""));
    }
    @DisplayName("корректно реагирует на пустой файл")
    @Test
    void findAllEmptyTest() {
        props.setCsvName("emptyTest.csv");
        CSVQuestionDAOImpl dao = new CSVQuestionDAOImpl(props);
        ArrayList<CSVQuestion> list = dao.findAll();
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("корректно реагирует на неверный формат файла с вопросами")
    @Test
    void findAllIncorrectTest() {
        props.setCsvName("incorrectTest.csv");
        CSVQuestionDAOImpl dao = new CSVQuestionDAOImpl(props);
        assertThrows(RuntimeException.class, () -> {dao.findAll();});
    }

    @DisplayName("корректно реагирует на верный формат файла с вопросами")
    @Test
    void findAllCorrectTest() {
        props.setCsvName("correctTest.csv");
        ArrayList<CSVQuestion> expectedList = new ArrayList<CSVQuestion>() {{
            add((CSVQuestion) new CSVQuestionMock("1",3));
            add((CSVQuestion) new CSVQuestionMock("2",5));
        }};
        ArrayList<CSVQuestion> findList = new CSVQuestionDAOImpl(props).findAll();
        assertThat(expectedList).isEqualTo(findList);
    }
}