package ru.otus.spring.kermilov.TestStudents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.kermilov.TestStudents.config.AppProps;

@Service
@RequiredArgsConstructor
public class LocalPrintServiceImpl implements PrintService {
    private final MessageSource messageSource;
    private final AppProps props;

    @Override
    public void print(String s) {
        System.out.println(messageSource.getMessage(s, null, props.getLocale()));
    }

    @Override
    public void print(String s, Object[] objects) {
        System.out.println(messageSource.getMessage(s, objects, props.getLocale()));
    }
}
