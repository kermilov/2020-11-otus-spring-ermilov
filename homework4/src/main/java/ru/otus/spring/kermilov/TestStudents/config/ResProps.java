package ru.otus.spring.kermilov.TestStudents.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "resources")
public class ResProps {
    @Getter
    @Setter
    private String csvName;
}
