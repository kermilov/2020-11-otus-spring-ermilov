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

import ru.otus.spring.kermilov.migratebooks.domain.GenreJpa;
import ru.otus.spring.kermilov.migratebooks.domain.GenreMongo;
import ru.otus.spring.kermilov.migratebooks.service.GenreMigrate;


//@SuppressWarnings("all")
@Configuration
public class ImportGenreJobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String OUTPUT_FILE_NAME = "outputFileName";
    public static final String INPUT_FILE_NAME = "inputFileName";
    public static final String IMPORT_GENRE_JOB_NAME = "importGenreJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory emf;

    @StepScope
    @Bean
    public MongoItemReader<GenreMongo> mongoItemReaderGenre(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<GenreMongo>()
                .name("mongoItemReaderGenre")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(GenreMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<GenreMongo, GenreJpa> processor(GenreMigrate genreMigrate) {
        return genreMigrate::migrate;
    }

    @StepScope
    @Bean
    public JpaItemWriter<GenreJpa> writer() {
        JpaItemWriter<GenreJpa> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public Job importGenreJob(Step genreMigrationStep) {
        return jobBuilderFactory.get(IMPORT_GENRE_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(genreMigrationStep)
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
    public Step genreMigrationStep(JpaItemWriter<GenreJpa> writer, ItemReader<GenreMongo> reader, ItemProcessor<GenreMongo, GenreJpa> itemProcessor) {
        return stepBuilderFactory.get("GenreMigrationStep")
                .<GenreMongo,GenreJpa>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListenerDefaultImpl<GenreMongo>())
                .listener(new ItemWriteListener<GenreJpa>() {
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
                .listener(new ItemProcessListener<GenreMongo, GenreJpa>() {
                    public void beforeProcess(GenreMongo o) {
                        logger.info("Начало обработки");
                    }

                    public void afterProcess(GenreMongo o, GenreJpa o2) {
                        logger.info("Конец обработки");
                    }

                    public void onProcessError(GenreMongo o, Exception e) {
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
