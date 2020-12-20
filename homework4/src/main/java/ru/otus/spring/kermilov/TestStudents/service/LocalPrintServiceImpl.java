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
        System.out.println(prepareToPrint(s));
    }

    @Override
    public void print(String s, Object[] objects) {
        System.out.println(prepareToPrint(s, objects));
    }

    public String prepareToPrint(String s) {
        return messageSource.getMessage(s, null, props.getLocale());
    }
    public String prepareToPrint(String s, Object[] objects) {
        return messageSource.getMessage(s, objects, props.getLocale());
    }
}
