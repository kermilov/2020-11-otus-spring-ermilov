package ru.otus.spring.kermilov.TestStudents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.kermilov.TestStudents.config.AppProps;

@Service
@RequiredArgsConstructor
public class LocalPrintServiceImpl implements LocalPrintService {
    private final MessageSource messageSource;
    private final AppProps props;

    @Override
    public void localPrint(String s) {
        System.out.println(messageSource.getMessage(s, null, props.getLocale()));
    }

    @Override
    public void localPrint(String s, Object[] objects) {
        System.out.println(messageSource.getMessage(s, objects, props.getLocale()));
    }
}
