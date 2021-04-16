package ru.otus.spring.kermilov.migratebooks.config;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import ru.otus.spring.kermilov.migratebooks.domain.AuthorJpa;
import ru.otus.spring.kermilov.migratebooks.domain.AuthorMongo;
import ru.otus.spring.kermilov.migratebooks.service.AuthorMigrate;


//@SuppressWarnings("all")
@Configuration
public class ImportAuthorJobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String OUTPUT_FILE_NAME = "outputFileName";
    public static final String INPUT_FILE_NAME = "inputFileName";
    public static final String IMPORT_AUTHOR_JOB_NAME = "importAuthorJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory emf;

    @StepScope
    @Bean
    public MongoItemReader<AuthorMongo> mongoItemReaderAuthor(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<AuthorMongo>()
                .name("mongoItemReaderAuthor")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(AuthorMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<AuthorMongo, AuthorJpa> processorAuthor(AuthorMigrate authorMigrate) {
        return authorMigrate::migrate;
    }

    @StepScope
    @Bean
    public JpaItemWriter<AuthorJpa> writerAuthor() {
        JpaItemWriter<AuthorJpa> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public Job importAuthorJob(Step authorMigrationStep) {
        return jobBuilderFactory.get(IMPORT_AUTHOR_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(authorMigrationStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step authorMigrationStep(JpaItemWriter<AuthorJpa> writer, ItemReader<AuthorMongo> reader, ItemProcessor<AuthorMongo, AuthorJpa> itemProcessor) {
        return stepBuilderFactory.get("AuthorMigrationStep")
                .<AuthorMongo,AuthorJpa>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListenerDefaultImpl<AuthorMongo>())
                .listener(new ItemWriteListener<AuthorJpa>() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<AuthorMongo, AuthorJpa>() {
                    public void beforeProcess(AuthorMongo o) {
                        logger.info("Начало обработки");
                    }

                    public void afterProcess(AuthorMongo o, AuthorJpa o2) {
                        logger.info("Конец обработки");
                    }

                    public void onProcessError(AuthorMongo o, Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
//                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }
}
