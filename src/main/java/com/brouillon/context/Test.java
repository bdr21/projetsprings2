package com.brouillon.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class Test {

    @Autowired
    private LocalTime st;

    @Bean(name = "et")
    public LocalTime endTime() {
        return st.plusMinutes(5);
    }
}
