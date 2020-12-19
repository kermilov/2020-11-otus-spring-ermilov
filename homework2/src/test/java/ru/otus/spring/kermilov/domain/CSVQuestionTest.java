package ru.otus.spring.kermilov.domain;

import org.junit.jupiter.api.Test;
import ru.otus.spring.kermilov.mock.CSVQuestionMock;

import static org.assertj.core.api.Assertions.assertThat;

class CSVQuestionTest {

    @Test
    void equalsIncorrectTest() {
        Object q1 = new Object();
        CSVQuestion q2 = new CSVQuestionMock("2");
        assertThat(q1.equals(q2)).isEqualTo(false);
    }

    @Test
    void equalsTrueTest() {
        CSVQuestion q1 = new CSVQuestionMock("1");
        CSVQuestion q2 = new CSVQuestionMock("1");
        assertThat(q1.equals(q2)).isEqualTo(true);
    }

    @Test
    void equalsFalseTest() {
        CSVQuestion q1 = new CSVQuestionMock("1");
        CSVQuestion q2 = new CSVQuestionMock("2");
        assertThat(q1.equals(q2)).isEqualTo(false);
    }
}