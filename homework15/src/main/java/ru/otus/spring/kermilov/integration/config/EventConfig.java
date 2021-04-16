package ru.otus.spring.kermilov.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@Configuration
public class EventConfig {
    public static final String GOALS_CHANNEL = "goalsChannel";
    public static final String ACTION_CHANNEL = "actionChannel";

    @Bean
    public QueueChannel goalsChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean
    public PublishSubscribeChannel actionChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate( 100 ).maxMessagesPerPoll( 2 ).get();
    }

    @Bean
    public IntegrationFlow gameFlow() {
        return IntegrationFlows.from( GOALS_CHANNEL )
                .split()
                .handle( "gameService", "game" )
                .aggregate()
                .channel( ACTION_CHANNEL )
                .get();
    }
}
