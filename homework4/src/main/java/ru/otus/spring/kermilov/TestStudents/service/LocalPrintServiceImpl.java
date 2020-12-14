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
        System.out.println(localString(s));
    }

    @Override
    public void localPrint(String s, Object[] objects) {
        System.out.println(localString(s, objects));
    }

    public String localString(String s) {
        return messageSource.getMessage(s, null, props.getLocale());
    }
    public String localString(String s, Object[] objects) {
        return messageSource.getMessage(s, objects, props.getLocale());
    }
}
