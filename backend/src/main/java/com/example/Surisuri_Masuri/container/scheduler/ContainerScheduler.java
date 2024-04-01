package com.example.Surisuri_Masuri.container.scheduler;

import com.example.Surisuri_Masuri.container.service.ContainerSchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ContainerScheduler {
    private final ContainerSchedulingService containerSchedulingService;

    @Scheduled(cron = "*/30 * 11 * * *") // 1초에 1000
    public void test() throws IOException {
        System.out.println("test");
        containerSchedulingService.discardExpiredFoodProducts();
    }
}
