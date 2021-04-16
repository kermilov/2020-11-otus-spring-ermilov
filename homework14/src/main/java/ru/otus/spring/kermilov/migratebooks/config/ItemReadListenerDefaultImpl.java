package ru.otus.spring.kermilov.migratebooks.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class ItemReadListenerDefaultImpl<T> implements ItemReadListener<T> {

    final Logger logger = LoggerFactory.getLogger("Batch");

    public void beforeRead() {
        this.logger.info("Начало чтения");
    }

    @Override
    public void afterRead(T o) {
        this.logger.info("Конец чтения");
    }

    public void onReadError(Exception e) {
        this.logger.info("Ошибка чтения");
    }
}