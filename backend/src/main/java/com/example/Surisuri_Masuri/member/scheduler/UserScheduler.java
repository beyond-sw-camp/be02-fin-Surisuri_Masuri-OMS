package com.example.Surisuri_Masuri.member.scheduler;

import com.example.Surisuri_Masuri.member.Service.UserSchedulingService;
import com.example.Surisuri_Masuri.storeStock.Service.StoreSchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserScheduler {
    private final UserSchedulingService userSchedulingService;

    @Scheduled(cron = "*/30 * 23 * * *\n") // 1초에 1000
    public void test() throws IOException {
        System.out.println("test");
        userSchedulingService.resetFirstLogin();
    }
}
