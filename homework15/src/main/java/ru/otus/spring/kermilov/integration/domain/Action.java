package ru.otus.spring.kermilov.integration.domain;

public class Action {

    private final String name;

    public Action(String name) {
        this.name = name;
    }

    public String getActionName() {
        return name;
    }
}
