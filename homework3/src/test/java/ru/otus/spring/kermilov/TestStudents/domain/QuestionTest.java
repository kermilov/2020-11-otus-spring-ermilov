package ru.otus.spring.kermilov.TestStudents.domain;

import org.junit.jupiter.api.Test;
import ru.otus.spring.kermilov.TestStudents.mock.QuestionMock;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    @Test
    void equalsIncorrectTest() {
        Object q1 = new Object();
        Question q2 = new QuestionMock("2");
        assertThat(q1.equals(q2)).isEqualTo(false);
    }
    
    @Test
    void equalsTrueTest() {
        Question q1 = new QuestionMock("1");
        Question q2 = new QuestionMock("1");
        assertThat(q1.equals(q2)).isEqualTo(true);
    }

    @Test
    void equalsFalseTest() {
        Question q1 = new QuestionMock("1");
        Question q2 = new QuestionMock("2");
        assertThat(q1.equals(q2)).isEqualTo(false);
    }
}