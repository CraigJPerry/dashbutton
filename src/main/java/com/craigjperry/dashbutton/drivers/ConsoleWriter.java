package com.craigjperry.dashbutton.drivers;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ConsoleWriter {
    private final Consumer<String> println;

    public ConsoleWriter() {
        this(System.out::println);
    }

    ConsoleWriter(Consumer<String> println) {
        this.println = println;
    }

    public void println(String message) {
        println.accept(message);
    }
}
