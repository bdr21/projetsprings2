package com.brouillon.data;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
public class DataSupplier implements Supplier<String> {

    @Override
    public String get() {
        return "Hello autowired";
    }

}
