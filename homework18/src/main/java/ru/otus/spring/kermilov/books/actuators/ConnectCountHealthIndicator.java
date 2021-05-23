package ru.otus.spring.kermilov.books.actuators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

@Component
public class ConnectCountHealthIndicator implements HealthIndicator {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public Health health() {
        var count = sessionRegistry.getAllPrincipals().stream()
                .filter(f -> !sessionRegistry.getAllSessions(f, false).isEmpty()).count();

        return count == 0 ? Health.down().build() : Health.up().withDetail("count", count).build();
    }

}
