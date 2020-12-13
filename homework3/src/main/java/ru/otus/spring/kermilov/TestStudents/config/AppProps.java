package ru.otus.spring.kermilov.TestStudents.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class AppProps {
    @Getter
    @Setter
    String csvName;
}
