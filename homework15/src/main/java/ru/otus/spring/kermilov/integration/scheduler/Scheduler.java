package ru.otus.spring.kermilov.integration.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.otus.spring.kermilov.integration.domain.Action;
import ru.otus.spring.kermilov.integration.domain.Goal;
import ru.otus.spring.kermilov.integration.event.Player;

@SuppressWarnings({ "resource", "Duplicates", "InfiniteLoopStatement" })
@Service
public class Scheduler {
    private static final String[] HIT = { "Закрученный удар", "Удар в падении через себя", "Удар скорпиона", "Удар наклбол", "Удар рабона", "Удар подъемом", "Удар головой" };

    private final Player player;

    public Scheduler(Player player) {
        this.player = player;
    }

    @Scheduled(fixedDelay = 7000)
    public void scheduleAction() {
        ForkJoinPool.commonPool().execute( () -> {
            Collection<Action> actions = generateActions();
            System.out.println( "New actions: " +
                    actions.stream().map( Action::getActionName )
                            .collect( Collectors.joining( ", " ) ) );
            Collection<Goal> goals = player.hit( actions );
            System.out.println( "Result actions: " + goals.stream()
                    .map( Goal::getName )
                    .collect( Collectors.joining( ", " ) ) );
        } );
    }

    private static Action generateAction() {
        return new Action( HIT[ RandomUtils.nextInt( 0, HIT.length ) ] );
    }

    private static Collection<Action> generateActions() {
        List<Action> actions = new ArrayList<>();
        for ( int i = 0; i < RandomUtils.nextInt( 1, 5 ); ++ i ) {
            actions.add( generateAction() );
        }
        return actions;
    }

}
