package com.brouillon.context;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class AnotherComponent {

    @Bean(name = "st")
    public LocalTime startTime() {
        return LocalTime.now();
    }
}
