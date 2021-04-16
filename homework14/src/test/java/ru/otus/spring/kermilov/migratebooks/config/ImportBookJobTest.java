package ru.otus.spring.kermilov.migratebooks.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.otus.spring.kermilov.migratebooks.dao.AuthorJpaDao;
import ru.otus.spring.kermilov.migratebooks.dao.BookJpaDao;
import ru.otus.spring.kermilov.migratebooks.dao.BookMongoDao;
import ru.otus.spring.kermilov.migratebooks.dao.GenreJpaDao;
import ru.otus.spring.kermilov.migratebooks.domain.AuthorJpa;
import ru.otus.spring.kermilov.migratebooks.domain.GenreJpa;

@SpringBootTest
class ImportBookJobTest {

    private static final String AUTHOR_12 = "Test Author 1";
    private static final String AUTHOR_22 = "Test Author 2";
    private static final String AUTHOR_32 = "Test Author 3";
    private static final String GENRE_12 = "Test Genre 1";
    private static final String GENRE_22 = "Test Genre 2";
    private static final String GENRE_32 = "Test Genre 3";
    private static final AuthorJpa AUTHOR_1 = new AuthorJpa(1, AUTHOR_12);
    private static final AuthorJpa AUTHOR_2 = new AuthorJpa(2, AUTHOR_22);
    private static final AuthorJpa AUTHOR_3 = new AuthorJpa(3, AUTHOR_32);
    private static final GenreJpa GENRE_1 = new GenreJpa(1, GENRE_12);
    private static final GenreJpa GENRE_2 = new GenreJpa(2, GENRE_22);
    private static final GenreJpa GENRE_3 = new GenreJpa(3, GENRE_32);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private BookJpaDao bookJpaDao;

    @Autowired
    private BookMongoDao bookMongoDao;

    @Autowired
    private AuthorJpaDao authorJpaDao;

    @Autowired
    private GenreJpaDao genreJpaDao;

    @Autowired
    private List<Job> jobList;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void clearMetaData() {
        var jobRepositoryTestUtils = new JobRepositoryTestUtils();
        jobRepositoryTestUtils.setDataSource(dataSource);
        jobRepositoryTestUtils.removeJobExecutions();
        authorJpaDao.saveAll(List.of(AUTHOR_1,AUTHOR_2,AUTHOR_3));
        genreJpaDao.saveAll(List.of(GENRE_1,GENRE_2,GENRE_3));
    }

    @Test
    void testJob() throws Exception {
        var jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(jobList.stream()
                .filter(job -> job.getName().equals(ImportBookJobConfig.IMPORT_BOOK_JOB_NAME)).findFirst().get());

        JobParameters parameters = new JobParametersBuilder()
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
        assertThat(bookJpaDao.findAll()).usingRecursiveComparison().ignoringFields("id","author.id","genres.id").isEqualTo(bookMongoDao.findAll());
    }
}