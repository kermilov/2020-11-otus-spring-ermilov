package ru.otus.spring.kermilov.TestStudents.domain;

import org.junit.jupiter.api.Test;
import ru.otus.spring.kermilov.TestStudents.mock.CSVQuestionMock;

import static org.assertj.core.api.Assertions.assertThat;

class CSVQuestionTest {

    @Test
    void compareTrueTest() {
        CSVQuestion q1 = new CSVQuestionMock("1");
        CSVQuestion q2 = new CSVQuestionMock("1");
        assertThat(q1.compare(q2)).isEqualTo(true);
    }

    @Test
    void compareFalseTest() {
        CSVQuestion q1 = new CSVQuestionMock("1");
        CSVQuestion q2 = new CSVQuestionMock("2");
        assertThat(q1.compare(q2)).isEqualTo(false);
    }
}