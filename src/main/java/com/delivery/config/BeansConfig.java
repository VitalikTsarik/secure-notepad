package com.delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

@Configuration
@EnableScheduling
public class BeansConfig {
    @Bean
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }
}
