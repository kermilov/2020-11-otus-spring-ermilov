package ru.otus.spring.kermilov.migratebooks.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kermilov.migratebooks.config.AppProps;


import static ru.otus.spring.kermilov.migratebooks.config.ImportAuthorJobConfig.*;
import static ru.otus.spring.kermilov.migratebooks.config.ImportGenreJobConfig.*;
import static ru.otus.spring.kermilov.migratebooks.config.ImportBookJobConfig.*;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final AppProps appProps;
    private final List<Job> importUserJob;

    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    //http://localhost:8080/h2-console/

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "start")
    public void startMigrationJobWithJobOperator() throws Exception {
        Long authorExecutionId = jobOperator.start(IMPORT_AUTHOR_JOB_NAME,"");
        Long genreExecutionId = jobOperator.start(IMPORT_GENRE_JOB_NAME,"");
        Long bookExecutionId = jobOperator.start(IMPORT_BOOK_JOB_NAME,"");
        System.out.println(jobOperator.getSummary(authorExecutionId));
        System.out.println(jobOperator.getSummary(genreExecutionId));
        System.out.println(jobOperator.getSummary(bookExecutionId));
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_AUTHOR_JOB_NAME));
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_GENRE_JOB_NAME));
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_BOOK_JOB_NAME));
    }
}
