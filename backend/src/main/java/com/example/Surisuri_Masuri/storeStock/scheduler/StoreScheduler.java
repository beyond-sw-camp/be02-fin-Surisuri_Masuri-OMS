package com.example.Surisuri_Masuri.storeStock.scheduler;

import com.example.Surisuri_Masuri.container.service.ContainerSchedulingService;
import com.example.Surisuri_Masuri.storeStock.Service.StoreSchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class StoreScheduler {
    private final StoreSchedulingService storeSchedulingService;

    @Scheduled(cron = "0 */1 * * * *\n") // 1분 간격으로 실행
    public void test() throws IOException {
        System.out.println("test");
        storeSchedulingService.discardExpiredFoodProducts();
    }
}
