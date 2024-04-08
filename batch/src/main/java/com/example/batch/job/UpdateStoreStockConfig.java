package com.example.batch.job;

import com.example.batch.Repository.StoreStockRepository;
import com.example.batch.entities.StoreStock;
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
public class UpdateStoreStockConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final StoreStockRepository repository;

    @Bean("updateStoreStockJob")
    public Job updateContainerStockJob(Step updateContainerStockStep) {
        return jobBuilderFactory.get("updateStoreStockJob")
                .incrementer(new RunIdIncrementer())
                .start(updateContainerStockStep)
                .build();
    }

    @JobScope
    @Bean("updateStoreStockStep")
    public Step updateStoreStockStep(ItemReader updateStoreStockReader,
                                         ItemProcessor updateStoreStockProcessor,
                                         ItemWriter updateStoreStockWriter) {
        return stepBuilderFactory.get("updateContainerStockStep")
                .<StoreStock, StoreStock>chunk(10)
                .reader(updateStoreStockReader)
                .processor(updateStoreStockProcessor)
                .writer(updateStoreStockWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<StoreStock> updateStoreStockReader() {
        System.out.println("Reader");

        LocalDate expirationDate = LocalDate.now();

        return new RepositoryItemReaderBuilder<StoreStock>()
                .name("updateStoreStockReader")
                .repository(repository)
                .methodName("findByExpiredAtAndIsDiscardedFalseAndProductIsItFoodTrue")
                .pageSize(10)
                .arguments(expirationDate)
                .sorts(Collections.singletonMap("idx", Sort.Direction.DESC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<StoreStock, StoreStock> updateStoreStockProcessor() {
        System.out.println("Processor");

        return storeStock -> {
            storeStock.setIsDiscarded(true);
            storeStock.setDiscardedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate());
            return storeStock;
        };
    }

    @StepScope
    @Bean
    public ItemWriter<StoreStock> updateStoreStockWriter() {
        System.out.println("Writer");

        return storeStocks -> {
            repository.saveAll(storeStocks);
            System.out.println("chunk end");
        };
    }
}
