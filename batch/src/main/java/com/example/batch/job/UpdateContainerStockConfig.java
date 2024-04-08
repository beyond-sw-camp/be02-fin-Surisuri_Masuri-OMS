package com.example.batch.job;

import com.example.batch.Repository.ContainerStockRepository;
import com.example.batch.entities.ContainerStock;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableTask
public class UpdateContainerStockConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ContainerStockRepository repository;

    @Bean("updateContainerStockJob")
    public Job updateContainerStockJob(Step updateContainerStockStep) {
        return jobBuilderFactory.get("updateContainerStockJob")
                .incrementer(new RunIdIncrementer())
                .start(updateContainerStockStep)
                .build();
    }

    @JobScope
    @Bean("updateContainerStockStep")
    public Step updateStoreStockStep(ItemReader updateContainerStockReader,
                                     ItemProcessor updateContainerStockProcessor,
                                     ItemWriter updateContainerStockWriter) {
        return stepBuilderFactory.get("updateContainerStockStep")
                .<ContainerStock, ContainerStock>chunk(10)
                .reader(updateContainerStockReader)
                .processor(updateContainerStockProcessor)
                .writer(updateContainerStockWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<ContainerStock> updateContainerStockReader() {
        System.out.println("Reader");

        LocalDate expirationDate = LocalDate.now().plusDays(7);

        return new RepositoryItemReaderBuilder<ContainerStock>()
                .name("updateContainerStockReader")
                .repository(repository)
                .methodName("findByExpiredAtAndIsDiscardedFalseAndProductIsItFoodTrue")
                .pageSize(10)
                .arguments(expirationDate)
                .sorts(Collections.singletonMap("idx", Sort.Direction.DESC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<ContainerStock, ContainerStock> updateContainerStockProcessor() {
        System.out.println("Processor");

        return containerStock -> {
            containerStock.setIsDiscarded(true);
            containerStock.setDiscardedAt(new Timestamp(System.currentTimeMillis()));
            return containerStock;
        };
    }

    @StepScope
    @Bean
    public ItemWriter<ContainerStock> updateContainerStockWriter() {
        System.out.println("Writer");

        return containerstocks -> {
            repository.saveAll(containerstocks);
            System.out.println("chunk end");
        };
    }
}
