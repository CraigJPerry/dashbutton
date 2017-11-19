package com.craigjperry.dashbutton.drivers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Component
public class ConsoleWriter {
    private final Consumer<String> println;

    public ConsoleWriter(Consumer<String> println) {
        this.println = println;
    }

    public void println(String message) {
        println.accept(message);
    }
}
