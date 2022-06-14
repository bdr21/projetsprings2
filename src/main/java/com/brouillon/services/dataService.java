package com.brouillon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service("myDS")
public class dataService implements Runnable {

    @Autowired
    private Supplier<String> supplier;

    @Override
    public void run() {
        System.out.println(supplier.get());
    }
}
