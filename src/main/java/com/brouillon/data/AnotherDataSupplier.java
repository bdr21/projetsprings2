package com.brouillon.data;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Primary
@Repository
public class AnotherDataSupplier implements Supplier<String> {

    @Override
    public String get() {
        return "Hello two suppliers";
    }

}
