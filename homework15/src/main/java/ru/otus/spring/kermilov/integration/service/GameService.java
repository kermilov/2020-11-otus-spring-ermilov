package ru.otus.spring.kermilov.integration.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import ru.otus.spring.kermilov.integration.domain.Action;
import ru.otus.spring.kermilov.integration.domain.Goal;

@Service
public class GameService {

    public Goal game(Action action) throws Exception {
        System.out.println("Reacting on " + action.getActionName());
        Thread.sleep(1000);
        System.out.println("Reacting on " + action.getActionName() + " done");
        return new Goal(RandomUtils.nextBoolean());
    }
}
