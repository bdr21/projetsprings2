package com.brouillon.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
public class ConfigCompo {

    @Bean
    public LocalTime startTime() {
        return LocalTime.now();
    }

    @Bean
    public LocalTime endTime() {
        return startTime().plusMinutes(1);
    }
}
