package com.demo.app.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Scheduler {
private int limit;

    @Scheduled(cron = "10 0 1 * *")
    public int scheduledTask(){
        limit = 5;
        return limit;
    }

}
