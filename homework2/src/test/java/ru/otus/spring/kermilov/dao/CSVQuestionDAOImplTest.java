package ru.otus.spring.kermilov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.kermilov.domain.CSVQuestion;
import ru.otus.spring.kermilov.mock.CSVQuestionMock;

import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс DAO")
class CSVQuestionDAOImplTest {

    @DisplayName("корректно реагирует на пустой файл")
    @Test
    void findAllEmptyTest() {
        CSVQuestionDAOImpl dao = new CSVQuestionDAOImpl("emptyTest.csv");
        ArrayList<CSVQuestion> list = dao.findAll();
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("корректно реагирует на неверный формат файла с вопросами")
    @Test
    void findAllIncorrectTest() {
        CSVQuestionDAOImpl dao = new CSVQuestionDAOImpl("incorrectTest.csv");
        assertThrows(RuntimeException.class, () -> {dao.findAll();});
    }

    @DisplayName("корректно реагирует на верный формат файла с вопросами")
    @Test
    void findAllCorrectTest() {
        ArrayList<CSVQuestion> expectedList = new ArrayList<CSVQuestion>() {{
            add(new CSVQuestionMock("1",3));
            add(new CSVQuestionMock("2",5));
        }};
        ArrayList<CSVQuestion> findList = new CSVQuestionDAOImpl("correctTest.csv").findAll();
        assertThat(expectedList).isEqualTo(findList);
    }
}