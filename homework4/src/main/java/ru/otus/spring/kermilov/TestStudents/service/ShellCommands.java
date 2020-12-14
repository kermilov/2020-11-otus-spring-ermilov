package ru.otus.spring.kermilov.TestStudents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final QAService qa;
    private final LocalPrintService lps;
    private String userName;

    @ShellMethod(value = "Login command.", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return lps.localString("hello.user",new String[]{userName});
    }

    @ShellMethod(value = "Start test.", key = {"s", "start"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public int startTest() {
        return qa.testStudent();
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null? Availability.unavailable(lps.localString("please.introduce.yourself")): Availability.available();
    }
}
