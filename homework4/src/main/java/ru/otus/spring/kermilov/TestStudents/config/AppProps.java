package ru.otus.spring.kermilov.TestStudents.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProps {
    @Getter
    @Setter
    String csvName;
    @Getter
    @Setter
    Locale locale;
}
