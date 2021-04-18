package ru.otus.spring.kermilov.integration.domain;


public class Goal {

    private final Boolean result;

    public Goal(Boolean result) {
        this.result = result;
    }

    public String getName() {
        return result ? "Гол!" : "Мазила!";
    }
}
