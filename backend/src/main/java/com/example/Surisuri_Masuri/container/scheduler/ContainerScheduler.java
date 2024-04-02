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

    @Scheduled(cron = "0 */1 * * * *\n") // 1분 간격으로 실행
    public void test() throws IOException {
        System.out.println("test");
        containerSchedulingService.discardExpiredFoodProducts();
    }
}
