package ru.otus.spring.kermilov.migratebooks.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.sql.DataSource;

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

import ru.otus.spring.kermilov.migratebooks.dao.AuthorJpaDao;
import ru.otus.spring.kermilov.migratebooks.dao.AuthorMongoDao;

@SpringBootTest
class ImportAuthorJobTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private AuthorJpaDao authorJpaDao;

    @Autowired
    private AuthorMongoDao authorMongoDao;

    @Autowired
    private List<Job> jobList;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void clearMetaData() {
        var jobRepositoryTestUtils = new JobRepositoryTestUtils();
        jobRepositoryTestUtils.setDataSource(dataSource);
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        var jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(jobList.stream()
                .filter(job -> job.getName().equals(ImportAuthorJobConfig.IMPORT_AUTHOR_JOB_NAME)).findFirst().get());

        JobParameters parameters = new JobParametersBuilder()
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
        assertThat(authorJpaDao.findAll()).usingRecursiveComparison().ignoringFields("id").isEqualTo(authorMongoDao.findAll());
    }
}