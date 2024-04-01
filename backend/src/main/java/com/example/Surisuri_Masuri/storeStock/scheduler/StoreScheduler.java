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

    @Scheduled(cron = "*/30 * 11 * * *") // 오전 11시부터 12시까지 매 30초마다
    public void test() throws IOException {
        System.out.println("test");
        storeSchedulingService.discardExpiredFoodProducts();
    }
}
