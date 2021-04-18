package ru.otus.spring.kermilov.integration.event;

import java.util.Collection;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import ru.otus.spring.kermilov.integration.config.EventConfig;
import ru.otus.spring.kermilov.integration.domain.Action;
import ru.otus.spring.kermilov.integration.domain.Goal;

@MessagingGateway
public interface Player {

    @Gateway(requestChannel = EventConfig.GOALS_CHANNEL, replyChannel = EventConfig.ACTION_CHANNEL)
    Collection<Goal> hit(Collection<Action> actions);
}
