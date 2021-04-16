package ru.otus.spring.kermilov.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@IntegrationComponentScan
@ComponentScan
@EnableIntegration
@EnableScheduling
public class App {

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);

    }

}
